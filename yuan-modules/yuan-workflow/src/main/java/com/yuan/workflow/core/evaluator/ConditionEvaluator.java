package com.yuan.workflow.core.evaluator;


import com.yuan.workflow.domain.enums.ConditionOperator;
import com.yuan.workflow.model.Expression;

import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class ConditionEvaluator {

    /**
     * 评估一个条件组（支持 AND/OR 嵌套）
     */
    public boolean evaluate(ConditionGroup root, Map<String, Object> vars) {
        if (root == null) return true; // 没条件就放行（按你业务可改 false）
        if (vars == null) vars = Collections.emptyMap();

        ConditionGroup.Relation rel = Optional.ofNullable(root.getRelation()).orElse(ConditionGroup.Relation.AND);

        // 先算本层表达式
        List<Boolean> results = new ArrayList<>();
        if (root.getExpressions() != null) {
            for (Expression exp : root.getExpressions()) {
                results.add(match(exp, vars));
                // 短路
                if (rel == ConditionGroup.Relation.AND && results.get(results.size() - 1) == false) return false;
                if (rel == ConditionGroup.Relation.OR && results.get(results.size() - 1) == true) return true;
            }
        }

        // 再算子组
        if (root.getGroups() != null) {
            for (ConditionGroup g : root.getGroups()) {
                boolean r = evaluate(g, vars);
                results.add(r);
                // 短路
                if (rel == ConditionGroup.Relation.AND && !r) return false;
                if (rel == ConditionGroup.Relation.OR && r) return true;
            }
        }

        // 如果本层没有任何条件，默认 true（按你业务可改 false）
        if (results.isEmpty()) return true;

        // 未短路到结论则汇总
        return rel == ConditionGroup.Relation.AND
                ? results.stream().allMatch(Boolean::booleanValue)
                : results.stream().anyMatch(Boolean::booleanValue);
    }

    /**
     * 单条表达式匹配
     */
    public boolean match(Expression exp, Map<String, Object> vars) {
        if (exp == null) return true;
        if (exp.getField() == null || exp.getField().isBlank()) return false;
        if (exp.getOperator() == null) return false;

        Object actualRaw = vars.get(exp.getField());
        if (actualRaw == null) {
            return false;
        }

        String expectedRaw = exp.getValue(); // 前端传入
        ConditionOperator op = exp.getOperator();

        return switch (op) {
            case EQ -> eq(actualRaw, expectedRaw, exp.getValueType());
            case NE -> !eq(actualRaw, expectedRaw, exp.getValueType());

            case GT, GE, LT, LE -> compareNumberOrDate(actualRaw, expectedRaw, exp.getValueType(), op);

            case IN -> in(actualRaw, expectedRaw, exp.getValueType());
            case NOT_IN -> !in(actualRaw, expectedRaw, exp.getValueType());
        };
    }

    // ======================
    // 下面是可扩展的“比较策略”
    // ======================

    /**
     * EQ：默认做“字符串语义相等”（对业务更友好）
     * 如果 valueType 明确是 number/date/bool，可按类型做等值
     */
    protected boolean eq(Object actualRaw, String expectedRaw, String valueType) {
        if (expectedRaw == null) expectedRaw = "";

        // 预留：根据 valueType 做更严格的比较
        // number：用 BigDecimal compareTo == 0
        if ("number".equalsIgnoreCase(valueType)) {
            BigDecimal a = toBigDecimal(actualRaw);
            BigDecimal b = toBigDecimal(expectedRaw);
            return a != null && b != null && a.compareTo(b) == 0;
        }

        // boolean
        if ("boolean".equalsIgnoreCase(valueType) || "bool".equalsIgnoreCase(valueType)) {
            Boolean a = toBoolean(actualRaw);
            Boolean b = toBoolean(expectedRaw);
            return a != null && b != null && a.equals(b);
        }

        // date/datetime
        if ("date".equalsIgnoreCase(valueType) || "datetime".equalsIgnoreCase(valueType)) {
            Instant a = toInstant(actualRaw);
            Instant b = toInstant(expectedRaw);
            return a != null && b != null && a.equals(b);
        }

        // 默认：字符串化后相等
        return String.valueOf(actualRaw).equals(expectedRaw);
    }

    /**
     * GT/GE/LT/LE：默认优先按 number 比较；如果 valueType= date/datetime 则按时间比较
     */
    protected boolean compareNumberOrDate(Object actualRaw, String expectedRaw, String valueType, ConditionOperator op) {
        if ("date".equalsIgnoreCase(valueType) || "datetime".equalsIgnoreCase(valueType)) {
            Instant a = toInstant(actualRaw);
            Instant b = toInstant(expectedRaw);
            if (a == null || b == null) return false;
            int cmp = a.compareTo(b);
            return switch (op) {
                case GT -> cmp > 0;
                case GE -> cmp >= 0;
                case LT -> cmp < 0;
                case LE -> cmp <= 0;
                default -> false;
            };
        }

        BigDecimal a = toBigDecimal(actualRaw);
        BigDecimal b = toBigDecimal(expectedRaw);
        if (a == null || b == null) return false;

        int cmp = a.compareTo(b);
        return switch (op) {
            case GT -> cmp > 0;
            case GE -> cmp >= 0;
            case LT -> cmp < 0;
            case LE -> cmp <= 0;
            default -> false;
        };
    }

    /**
     * IN / NOT_IN：
     * - expectedRaw 支持："1,2,3" / "A|B|C" / "[1,2,3]"（简单 JSON array）
     * - 实际比较默认用“字符串语义”
     * 你后续也可根据 valueType 做 number/date 的包含比较
     */
    protected boolean in(Object actualRaw, String expectedRaw, String valueType) {
        if (expectedRaw == null) return false;

        // 解析 expected 为候选集合
        List<String> candidates = parseToStringList(expectedRaw);
        if (candidates.isEmpty()) return false;

        // 预留：number/date/bool 的 contains 逻辑
        if ("number".equalsIgnoreCase(valueType)) {
            BigDecimal a = toBigDecimal(actualRaw);
            if (a == null) return false;
            for (String c : candidates) {
                BigDecimal b = toBigDecimal(c);
                if (b != null && a.compareTo(b) == 0) return true;
            }
            return false;
        }

        if ("boolean".equalsIgnoreCase(valueType) || "bool".equalsIgnoreCase(valueType)) {
            Boolean a = toBoolean(actualRaw);
            if (a == null) return false;
            for (String c : candidates) {
                Boolean b = toBoolean(c);
                if (b != null && a.equals(b)) return true;
            }
            return false;
        }

        if ("date".equalsIgnoreCase(valueType) || "datetime".equalsIgnoreCase(valueType)) {
            Instant a = toInstant(actualRaw);
            if (a == null) return false;
            for (String c : candidates) {
                Instant b = toInstant(c);
                if (b != null && a.equals(b)) return true;
            }
            return false;
        }

        // 默认：字符串 contains
        String actual = String.valueOf(actualRaw);
        return candidates.stream().anyMatch(actual::equals);
    }

    // ======================
    // 类型转换工具
    // ======================

    protected BigDecimal toBigDecimal(Object val) {
        if (val == null) return null;
        try {
            if (val instanceof BigDecimal bd) return bd;
            if (val instanceof Integer i) return BigDecimal.valueOf(i.longValue());
            if (val instanceof Long l) return BigDecimal.valueOf(l);
            if (val instanceof Double d) return BigDecimal.valueOf(d);
            if (val instanceof Float f) return BigDecimal.valueOf(f.doubleValue());
            if (val instanceof Number n) return new BigDecimal(n.toString());
            String s = val.toString().trim();
            if (s.isEmpty()) return null;
            return new BigDecimal(s);
        } catch (Exception e) {
            return null;
        }
    }

    protected Boolean toBoolean(Object val) {
        if (val == null) return null;
        if (val instanceof Boolean b) return b;
        String s = val.toString().trim().toLowerCase();
        if (s.isEmpty()) return null;
        if ("true".equals(s) || "1".equals(s) || "yes".equals(s) || "y".equals(s)) return true;
        if ("false".equals(s) || "0".equals(s) || "no".equals(s) || "n".equals(s)) return false;
        return null;
    }

    /**
     * 支持：
     * - Instant / Date / LocalDate / LocalDateTime / ZonedDateTime
     * - 字符串：ISO-8601（2026-01-10T12:00:00Z / 2026-01-10T20:00:00+08:00）
     * - 字符串：yyyy-MM-dd / yyyy-MM-dd HH:mm:ss（按系统默认时区）
     */
    protected Instant toInstant(Object val) {
        if (val == null) return null;

        try {
            if (val instanceof Instant i) return i;
            if (val instanceof Date d) return d.toInstant();
            if (val instanceof LocalDate ld) return ld.atStartOfDay(ZoneId.systemDefault()).toInstant();
            if (val instanceof LocalDateTime ldt) return ldt.atZone(ZoneId.systemDefault()).toInstant();
            if (val instanceof ZonedDateTime zdt) return zdt.toInstant();
            if (val instanceof OffsetDateTime odt) return odt.toInstant();

            String s = val.toString().trim();
            if (s.isEmpty()) return null;

            // ISO
            try { return Instant.parse(s); } catch (Exception ignored) {}
            try { return OffsetDateTime.parse(s).toInstant(); } catch (Exception ignored) {}
            try { return ZonedDateTime.parse(s).toInstant(); } catch (Exception ignored) {}
            try { return LocalDateTime.parse(s).atZone(ZoneId.systemDefault()).toInstant(); } catch (Exception ignored) {}
            try { return LocalDate.parse(s).atStartOfDay(ZoneId.systemDefault()).toInstant(); } catch (Exception ignored) {}

            // 常见格式
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            try { return LocalDateTime.parse(s, dtf).atZone(ZoneId.systemDefault()).toInstant(); } catch (Exception ignored) {}

            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            try { return LocalDate.parse(s, df).atStartOfDay(ZoneId.systemDefault()).toInstant(); } catch (Exception ignored) {}

            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 解析 IN 值：
     * - "a,b,c"（逗号）
     * - "a|b|c"（竖线）
     * - "[a,b,c]" 或 ["a","b"]（简单 json-array 风格，轻量处理，不依赖 JSON 库）
     */
    protected List<String> parseToStringList(String raw) {
        String s = raw.trim();
        if (s.isEmpty()) return List.of();

        // 简单 JSON array：["a","b"] / [1,2,3]
        if (s.startsWith("[") && s.endsWith("]")) {
            String inner = s.substring(1, s.length() - 1).trim();
            if (inner.isEmpty()) return List.of();
            // 这里不做完整 JSON 解析，只做简单 split（足够覆盖你前端常见输入）
            return Arrays.stream(inner.split(","))
                    .map(String::trim)
                    .map(this::stripQuotes)
                    .filter(x -> !x.isEmpty())
                    .collect(Collectors.toList());
        }

        // 分隔符：优先逗号，其次竖线
        String delimiter = s.contains(",") ? "," : (s.contains("|") ? "\\|" : null);
        if (delimiter == null) {
            return List.of(stripQuotes(s));
        }

        return Arrays.stream(s.split(delimiter))
                .map(String::trim)
                .map(this::stripQuotes)
                .filter(x -> !x.isEmpty())
                .collect(Collectors.toList());
    }

    protected String stripQuotes(String s) {
        if (s == null) return "";
        String t = s.trim();
        if ((t.startsWith("\"") && t.endsWith("\"")) || (t.startsWith("'") && t.endsWith("'"))) {
            return t.substring(1, t.length() - 1).trim();
        }
        return t;
    }
}
