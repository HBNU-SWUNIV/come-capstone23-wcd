package com.wcd.openvidu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OpenviduApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenviduApplication.class, args);
	}

}
