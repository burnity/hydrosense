# Stage 1: Build da aplicacao
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Define o diretorio de trabalho
WORKDIR /app

# Copia os arquivos do projeto
COPY pom.xml .
COPY src ./src

# Executa o build do projeto (pula testes para build mais rapido)
RUN mvn clean package -DskipTests

# Stage 2: Imagem final com o JAR
FROM eclipse-temurin:17-jre-alpine

# Instala wget para healthcheck
RUN apk add --no-cache wget

# Define o diretorio de trabalho
WORKDIR /app

# Copia o JAR gerado no stage anterior
COPY --from=build /app/target/*.jar app.jar

# Expoe a porta da aplicacao
EXPOSE 8080

# Define variaveis de ambiente padrao (podem ser sobrescritas)
ENV SPRING_PROFILES_ACTIVE=default
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Comando para executar a aplicacao
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
