FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY build/libs/fcmservice-1.0.jar FCMService.jar
ENTRYPOINT ["java","-jar","FCMService.jar"]