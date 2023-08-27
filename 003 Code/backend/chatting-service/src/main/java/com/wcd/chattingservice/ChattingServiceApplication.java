package com.wcd.chattingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class ChattingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChattingServiceApplication.class, args);
	}
}
