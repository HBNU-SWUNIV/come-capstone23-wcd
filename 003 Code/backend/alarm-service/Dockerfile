FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY build/libs/alarm-service-1.0.jar AlarmService.jar
ENTRYPOINT ["java","-jar","AlarmService.jar"]