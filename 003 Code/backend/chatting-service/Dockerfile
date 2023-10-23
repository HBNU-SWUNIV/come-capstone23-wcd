FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY build/libs/chatting-service.jar ChattingService.jar
ENTRYPOINT ["java","-jar","ChattingService.jar"]