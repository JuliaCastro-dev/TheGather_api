FROM eclipse-temurin:21-jdk as build

# build phase
COPY . /api/.
WORKDIR /api
RUN chmod +x ./mvnw && ./mvnw -DoutputFile=target/mvn-dependency-list.log -B -DskipTests clean dependency:list install

FROM eclipse-temurin:21-jre as runtime
WORKDIR /app
COPY --from=build /api/target/*.jar /api/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
