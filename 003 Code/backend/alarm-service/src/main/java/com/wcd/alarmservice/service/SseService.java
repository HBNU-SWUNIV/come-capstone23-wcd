package com.wcd.alarmservice.service;

import com.wcd.alarmservice.client.ClubServiceClient;
import com.wcd.alarmservice.client.UserServiceClient;
import com.wcd.alarmservice.dto.feignclient.ResponseClubMemberIdsByClubId;
import com.wcd.alarmservice.dto.request.RequestJoinClub;
import com.wcd.alarmservice.repository.EmitterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class SseService {
    // 기본 타임아웃 설정
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    private final EmitterRepository emitterRepository;
    private final ClubServiceClient clubServiceClient;
    private final UserServiceClient userServiceClient;

    /**
     * 클라이언트가 연결을 위해 호출하는 메서드.
     *
     * @param userId - 구독하는 클라이언트의 사용자 아이디.
     * @return SseEmitter - 서버에서 보낸 이벤트 Emitter
     */
    public SseEmitter connect(Long userId) {
        SseEmitter emitter = createEmitter(userId);

        sendToClient(userId, "Connected! [userId=" + userId + "]");
        return emitter;
    }

    /**
     * 서버의 이벤트를 클라이언트에게 보내는 메서드 (모임 가입 알람)
     *
     * @param requestJoinClub - 메세지를 전송할 사용자의 모임 아이디, 사용자 아이디
     */
    public void notifyJoinClub(RequestJoinClub requestJoinClub) {
        sendToClubMembers(requestJoinClub);
    }

    public void notifyCreateSchedule(Long clubId) {
        sendToClubMembers(clubId);
    }

    /**
     * 클라이언트에게 데이터를 전송
     *
     * @param id   - 데이터를 받을 사용자의 아이디.
     * @param data - 전송할 데이터.
     */
    private void sendToClient(Long id, Object data) {
        SseEmitter emitter = emitterRepository.get(id);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().name("connect").data(data));
            } catch (IOException exception) {
                emitterRepository.deleteById(id);
                emitter.completeWithError(exception);
            }
        }
    }

    /**
     * 클라이언트에게 데이터를 전송
     *
     * @param requestJoinClub - 메세지를 전송할 사용자의 모임 아이디, 사용자 아이디
     */
    private void sendToClubMembers(RequestJoinClub requestJoinClub) {
        ResponseClubMemberIdsByClubId clubMemberIds = clubServiceClient.getClubMemberIds(requestJoinClub.getClubId());
        String clubName = clubServiceClient.getClubNameById(requestJoinClub.getClubId());

        clubMemberIds.getUserIdList()
                .forEach(userId -> {
                    SseEmitter emitter = emitterRepository.get(userId);
                    String userName = userServiceClient.getUsernameById(userId);
                    if(emitter != null) {
                        try {
                            log.info("notifyJoinClubMember : {}", userId);
                            emitter.send(SseEmitter.event().name("notifyJoinClubMember").data(userName + "님 께서" + clubName + "에 가입했습니다."));
                        } catch (IOException exception) {
                            emitterRepository.deleteById(userId);
                            emitter.completeWithError(exception);
                        }
                    }
                });
    }

    private void sendToClubMembers(Long clubId) {
        ResponseClubMemberIdsByClubId clubMemberIds = clubServiceClient.getClubMemberIds(clubId);
        String clubName = clubServiceClient.getClubNameById(clubId);

        clubMemberIds.getUserIdList()
                .forEach(userId -> {
                    SseEmitter emitter = emitterRepository.get(userId);
                    if(emitter != null) {
                        try {
                            emitter.send(SseEmitter.event().name("notifyCreateSchedule").data( clubName + "에 일정이 등록되었습니다."));
                        } catch (IOException exception) {
                            emitterRepository.deleteById(userId);
                            emitter.completeWithError(exception);
                        }
                    }
                });
    }


    public void test(Long num) {
        SseEmitter emitter = emitterRepository.get(num);
        try {
            emitter.send(SseEmitter.event().name("test").data("test"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 사용자 아이디를 기반으로 이벤트 Emitter를 생성
     *
     * @param userId - 사용자 아이디.
     * @return SseEmitter - 생성된 이벤트 Emitter.
     */
    private SseEmitter createEmitter(Long userId) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(userId, emitter);

        // Emitter가 완료될 때(모든 데이터가 성공적으로 전송된 상태) Emitter를 삭제한다.
        emitter.onCompletion(() -> emitterRepository.deleteById(userId));
        // Emitter가 타임아웃 되었을 때(지정된 시간동안 어떠한 이벤트도 전송되지 않았을 때) Emitter를 삭제한다.
        emitter.onTimeout(() -> emitterRepository.deleteById(userId));

        return emitter;
    }
}
