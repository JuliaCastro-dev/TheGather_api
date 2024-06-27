# Use uma imagem base do JDK 21 para o build
FROM openjdk:21-jdk-slim as build

# build phase
COPY . /api
WORKDIR /api
RUN chmod +x ./mvnw && ./mvnw -DoutputFile=target/mvn-dependency-list.log -B -DskipTests clean dependency:list install

# Use uma imagem base do JRE 21 para o runtime
FROM openjdk:21-jre-slim as runtime
WORKDIR /api
COPY --from=build /api/target/*.jar /api/api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
