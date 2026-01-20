package com.yuan.common.oss.core.domin;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class ObjectKeyGenerator {

    public String generate(OssScope scope, String filename) {
        String safeName = safeFilename(filename);
        LocalDate d = LocalDate.now();

        String prefix = normalize(scope.getPrefix().getPrefix());
        String ns = normalize(scope.getNamespace().getSpace());

        // tenant/ns/prefix/yyyy/MM/dd/uuid_filename
        String base = String.format("%s/%s/%04d/%02d/%02d/%s_%s",
                scope.getTenantId(),
                ns,
                d.getYear(), d.getMonthValue(), d.getDayOfMonth(),
                UUID.randomUUID().toString().replace("-", ""),
                safeName
        );

        if (prefix.isBlank()) return base;
        return String.format("%s/%s/%s/%04d/%02d/%02d/%s_%s",
                scope.getTenantId(), ns, prefix,
                d.getYear(), d.getMonthValue(), d.getDayOfMonth(),
                UUID.randomUUID().toString().replace("-", ""),
                safeName
        );
    }

    private String normalize(String s) {
        if (s == null) return "";
        s = s.trim();
        while (s.startsWith("/")) s = s.substring(1);
        while (s.endsWith("/")) s = s.substring(0, s.length() - 1);
        return s;
    }

    private String safeFilename(String filename) {
        if (filename == null || filename.isBlank()) return "file";
        return filename.replaceAll("[\\\\/:*?\"<>|\\s]+", "_");
    }
}
