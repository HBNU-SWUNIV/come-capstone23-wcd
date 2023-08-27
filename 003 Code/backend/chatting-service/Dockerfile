FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY build/libs/chatting-service-1.0.jar ChattingService.jar
ENTRYPOINT ["java","-jar","ChattingService.jar"]