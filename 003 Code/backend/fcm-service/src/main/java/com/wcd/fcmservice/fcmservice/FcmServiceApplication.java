package com.wcd.fcmservice.fcmservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FcmServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FcmServiceApplication.class, args);
	}

}
