# Usa uma imagem base do Maven para compilar o projeto
FROM openjdk-8u111-jre-alpine AS runtime
WORKDIR /api

# Copia o arquivo pom.xml para baixar as dependências
COPY pom.xml .

# Baixa as dependências do Maven (cacheadas se o pom.xml não mudou)
RUN mvn dependency:go-offline

# Copia o código fonte para o container
COPY src ./src

# Compila o projeto usando o Maven
RUN mvn package -DskipTests

# Usa uma imagem base do OpenJDK para executar a aplicação
FROM openjdk-8u111-jre-alpine AS runtime
WORKDIR /api

# Copia o arquivo JAR construído no estágio de compilação
COPY --from=build /app/target/api-0.0.1-SNAPSHOT.jar ./api.jar

# Comando para executar a aplicação quando o contêiner for iniciado
CMD ["java", "-jar", "./api.jar"]
