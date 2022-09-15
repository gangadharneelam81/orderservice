FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} myorderservice.jar
EXPOSE 5000
ENTRYPOINT ["java","-jar","/myorderservice.jar"]