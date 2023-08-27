package com.wcd.alarmservice.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
public class EmitterRepository {
    // 모든 Emitters를 저장하는 ConcurrentHashMap
    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    /**
     * 주어진 아이디와 이미터를 저장
     *
     * @param userId      - 사용자 아이디.
     * @param emitter - 이벤트 Emitter.
     */
    public void save(Long userId, SseEmitter emitter) {
        emitters.put(userId, emitter);
    }

    /**
     * 주어진 아이디의 Emitter를 제거
     *
     * @param userId - 사용자 아이디.
     */
    public void deleteById(Long userId) {
        emitters.remove(userId);
    }

    /**
     * 주어진 아이디의 Emitter를 가져옴.
     *
     * @param userId - 사용자 아이디.
     * @return SseEmitter - 이벤트 Emitter.
     */
    public SseEmitter get(Long userId) {
        return emitters.get(userId);
    }
}

