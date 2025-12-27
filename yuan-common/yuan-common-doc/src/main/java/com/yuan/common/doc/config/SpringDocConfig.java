package com.yuan.common.doc.config;

import io.swagger.v3.oas.models.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.configuration.SpringDocConfiguration;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Swagger 文档配置
 *
 * 
 */
@RequiredArgsConstructor
@AutoConfiguration(before = SpringDocConfiguration.class)
@ConditionalOnProperty(name = "springdoc.api-docs.enabled", havingValue = "true", matchIfMissing = true)
public class SpringDocConfig {


    /**
     * 前端使用openapi生成接口时，将 long转换为string
     * @return
     */
    @Bean
    public OpenApiCustomizer longToStringOpenApiCustomizer() {
        return openApi -> {
            if (openApi.getComponents() == null
                    || openApi.getComponents().getSchemas() == null) {
                return;
            }
            openApi.getComponents().getSchemas().values().forEach(schema -> {
                Map properties = schema.getProperties();
                if (properties == null) return;
                Set set = properties.keySet();
                for (Object o : set) {
                    Object object = properties.get(o);
                    if (object instanceof Schema) {
                        Schema prop = (Schema) properties.get(o);
                        if (Objects.equals(prop.getFormat(), "int64") && prop.getTypes()!=null && !prop.getTypes().isEmpty() && prop.getTypes().contains("integer")) {
                            prop.setFormat(null);
                            prop.getTypes().remove("integer");
                            prop.getTypes().add("string");
                        }

                        if (prop.getTypes() != null && !prop.getTypes().isEmpty() && prop.getTypes().contains("array")) {
                            Schema items = prop.getItems();
                            Set itemsTypes = items.getTypes();
                            if (Objects.equals(items.getFormat(),"int64") && itemsTypes != null && !itemsTypes.isEmpty() && itemsTypes.contains("integer")) {
                                items.setFormat(null);
                                itemsTypes.remove("integer");
                                itemsTypes.add("string");
                            }
                        }
                    }

                }
            });
        };
    }

}
