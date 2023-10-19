package com.wcd.fcmservice.fcmservice.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

// 비공개 키 파일의 인증정보를 이용한 FirebaseApp 초기화
@Slf4j
@Component
public class FCMInitializer {

    @Value("${fcm.certification}")
    private String googleApplicationCredentials;

    // 빈 객체가 생성되고 의존성 주입이 완료된 후에 초기화가 실행될 수 있도록
    @PostConstruct
    public void initialize() throws IOException {
        ClassPathResource resource = new ClassPathResource(googleApplicationCredentials);

        try (InputStream is = resource.getInputStream()) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(is))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("FirebaseApp initialization complete");
            }
        }
    }
}