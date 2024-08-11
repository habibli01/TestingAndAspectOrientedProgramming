FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY ${JAR_FILE} build/libs/registration-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","build/libs/registration-0.0.1-SNAPSHOT.jar"]