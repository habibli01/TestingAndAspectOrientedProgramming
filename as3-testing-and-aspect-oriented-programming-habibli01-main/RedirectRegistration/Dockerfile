FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY ${JAR_FILE} build/libs/redirectregistration-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","build/libs/redirectregistration-0.0.1-SNAPSHOT.jar"]