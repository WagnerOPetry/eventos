# Backend multi-stage Dockerfile
# Build stage
FROM maven:3.9.4-eclipse-temurin-21 as build
WORKDIR /workspace/app
COPY pom.xml mvnw .mvn/ ./
COPY src ./src
# Use the wrapper to ensure consistent maven
RUN mvn -B -DskipTests package

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Copia o jar gerado pelo build (corrigido para o nome correto do artefato)
COPY --from=build /workspace/app/target/eventos-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]