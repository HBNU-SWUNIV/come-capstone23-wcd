FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
RUN mkdir /image
COPY build/libs/club-service-1.0.jar ClubService.jar
ENTRYPOINT ["java","-jar","ClubService.jar"]