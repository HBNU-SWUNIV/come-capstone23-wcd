package com.wcd.configservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/*
- 분산 시스템에서 서버 클라이언트 구성에 필요한 설정 정보(application.yml)를 외부 시스템에서 관리
- 하나의 중앙화 된 저장소에서 구성요소 관리 가능
- 각 서비스를 다시 빌드하지 않고, 바로 적용 가능
- 애플리케이션 배포 파이프라인을 통해 DEV-UAT- PROD 환경에 맞는 구성 정보 사용
 */
/*
application.yml, applicatin-name.yml, application-name-<profile>.yml 등
여러 설정 파일을 구성해놓고 각각의 마이크로서비스에서 필요한 구성정보를 가져온다.
 */

@SpringBootApplication
@EnableConfigServer
public class ConfigServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigServiceApplication.class, args);
	}

}
