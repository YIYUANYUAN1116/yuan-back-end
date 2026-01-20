package com.yuan.common.oss.core.provider;

import com.yuan.common.oss.enums.StorageType;
import com.yuan.common.oss.properties.OssProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StorageProviderRouter {

    private final OssProperties props;
    private final List<StorageProvider> providers;

    public StorageProvider current() {
        StorageType type = props.getStorage();
        return providers.stream()
                .filter(p -> p.type() == type)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No StorageProvider for: " + type));
    }
}
