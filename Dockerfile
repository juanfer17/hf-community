# Etapa de construcción
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copia el .jar generado desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Expone el puerto estándar de Spring Boot (lo usa Railway para el healthcheck)
EXPOSE 8080

# Ejecuta el JAR, usando el puerto que Railway provee en $PORT
ENTRYPOINT ["java", "-Dserver.port=${PORT}", "-jar", "app.jar"]
