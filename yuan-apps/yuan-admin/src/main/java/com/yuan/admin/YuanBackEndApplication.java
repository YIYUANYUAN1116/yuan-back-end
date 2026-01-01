package com.yuan.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.yuan"})
@EnableScheduling
@EnableAsync
public class YuanBackEndApplication {
    public static void main(String[] args) {
        SpringApplication.run(YuanBackEndApplication.class, args);

    }
}
