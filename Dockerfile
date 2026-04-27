# --------- STAGE 1: BUILD ---------
FROM maven:3.9.9-eclipse-temurin-17 AS builder

WORKDIR /app

# Copiar pom primero (mejora cache)
COPY pom.xml .

# Descargar dependencias
RUN mvn dependency:go-offline

# Copiar código
COPY src ./src

# Compilar sin tests
RUN mvn clean package -DskipTests

# --------- STAGE 2: RUN ---------
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copiar jar generado
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]