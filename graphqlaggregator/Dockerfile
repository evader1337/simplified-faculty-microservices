FROM openjdk:8-jre-alpine
WORKDIR /app
ADD ./target/graphql-aggregator-1.0-SNAPSHOT.jar /app
EXPOSE 8080
CMD ["java", "-jar", "graphql-aggregator-1.0-SNAPSHOT.jar"]