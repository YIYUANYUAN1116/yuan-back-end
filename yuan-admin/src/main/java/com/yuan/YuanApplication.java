package com.yuan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.yuan"})
@EnableScheduling
@EnableAsync
public class YuanApplication {
    public static void main(String[] args) {
        SpringApplication.run(YuanApplication.class, args);

    }
}
