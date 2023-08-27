package com.wcd.apigatewayservice.client;

//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//@FeignClient(name="club-service")
//public interface ClubServiceClient {
//
//    @GetMapping("/member/{user-id}/club/{club-id}")
//    boolean isUserMemberOfClub(@PathVariable("club-id") Long clubId, @PathVariable("user-id") Long userId);
//}


import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ClubServiceClient {

    private final WebClient webClient;

    public ClubServiceClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Boolean> isUserMemberOfClub(Long clubId, Long userId) {
        return webClient.get()
                .uri("lb://CLUB-SERVICE/clubs/{clubId}/members/{userId}", userId, clubId)
                .retrieve()
                .bodyToMono(Boolean.class);
    }
}