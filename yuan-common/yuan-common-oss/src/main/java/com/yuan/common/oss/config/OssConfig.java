package com.yuan.common.oss.config;

import com.yuan.common.oss.properties.OssProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;

@EnableConfigurationProperties(OssProperties.class)
@RequiredArgsConstructor
public class OssConfig {

    private final OssProperties props;

    @Bean
    public S3Client s3Client() {

        OssProperties.S3 m = props.getS3();
        return S3Client.builder()
                .endpointOverride(URI.create(m.getEndpoint()))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(m.getAccessKey(), m.getSecretKey())
                ))
                .region(Region.of(m.getRegion()))
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(Boolean.TRUE.equals(m.getPathStyleAccess()))
                        .build())
                .build();
    }

    @Bean
    public S3Presigner s3Presigner() {
        OssProperties.S3 s3 = props.getS3();
        return S3Presigner.builder()
                .endpointOverride(URI.create(s3.getEndpoint()))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(s3.getAccessKey(), s3.getSecretKey())
                ))
                .region(Region.of(s3.getRegion()))
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(Boolean.TRUE.equals(s3.getPathStyleAccess()))
                        .build())
                .build();
    }
}
