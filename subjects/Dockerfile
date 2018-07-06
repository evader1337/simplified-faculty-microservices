FROM openjdk:8-jre-alpine
WORKDIR /app
ADD ./target/subjects-1.0-SNAPSHOT.jar /app
EXPOSE 8082
CMD ["java", "-jar", "subjects-1.0-SNAPSHOT.jar"]