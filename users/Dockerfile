FROM openjdk:8-jre-alpine
WORKDIR /app
ADD ./target/users-1.0-SNAPSHOT.jar /app
EXPOSE 8081
CMD ["java", "-jar", "users-1.0-SNAPSHOT.jar"]