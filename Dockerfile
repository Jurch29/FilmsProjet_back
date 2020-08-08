FROM openjdk:8-jdk-alpine
RUN mkdir /app
COPY build/libs/*.jar /app/spring-boot-application.jar
ENTRYPOINT ["java","-jar","/app/spring-boot-application.jar"]