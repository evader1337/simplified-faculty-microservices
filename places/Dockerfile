FROM openjdk:8-jre-alpine
WORKDIR /app
ADD ./target/places-1.0-SNAPSHOT.jar /app
EXPOSE 8083
CMD ["java", "-jar", "places-1.0-SNAPSHOT.jar"]