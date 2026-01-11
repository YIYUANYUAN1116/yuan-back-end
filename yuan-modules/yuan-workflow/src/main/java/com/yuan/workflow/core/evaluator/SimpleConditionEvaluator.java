package com.yuan.workflow.core.evaluator;

import com.yuan.workflow.api.enums.ConditionOperator;
import com.yuan.workflow.model.Expression;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SimpleConditionEvaluator {

    public static boolean match(Expression exp, Map<String, Object> vars) {
        Object actualRaw = vars.get(exp.getField());
        if (actualRaw == null) {
            return false;
        }

        String expectedRaw = exp.getValue();
        ConditionOperator op = exp.getOperator();

        switch (op) {
            case EQ:
                return String.valueOf(actualRaw).equals(expectedRaw);

            case NE:
                return !String.valueOf(actualRaw).equals(expectedRaw);

            case GT:
            case GE:
            case LT:
            case LE: {
                BigDecimal actual = toBigDecimal(actualRaw);
                BigDecimal expected = toBigDecimal(expectedRaw);
                if (actual == null || expected == null) {
                    return false;
                }

                int cmp = actual.compareTo(expected);
                return switch (op) {
                    case GT -> cmp > 0;
                    case GE -> cmp >= 0;
                    case LT -> cmp < 0;
                    case LE -> cmp <= 0;
                    default -> false;
                };
            }

            case IN:
            case NOT_IN: {
                // value: "1,2,3" 或 "A,B,C"
                Set<String> expectedSet = Arrays.stream(expectedRaw.split(","))
                        .map(String::trim)
                        .collect(Collectors.toSet());

                boolean contains = expectedSet.contains(String.valueOf(actualRaw));
                return op == ConditionOperator.IN ? contains : !contains;
            }

            default:
                throw new IllegalStateException("Unsupported operator: " + op);
        }
    }

    private static BigDecimal toBigDecimal(Object val) {
        if (val == null) return null;

        try {
            if (val instanceof BigDecimal bd) {
                return bd;
            }
            if (val instanceof Number n) {
                return BigDecimal.valueOf(n.doubleValue());
            }
            return new BigDecimal(val.toString().trim());
        } catch (Exception e) {
            return null;
        }
    }
}
