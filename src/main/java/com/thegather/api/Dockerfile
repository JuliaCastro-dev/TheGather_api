#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /api/src
COPY pom.xml /api
RUN mvn -f "/api/pom.xml" clean package

#
# Package stage
#
FROM openjdk:11-jre-slim
COPY --from=build /api/target/api-0.0.1-SNAPSHOT.jar api.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/demo.jar"]


