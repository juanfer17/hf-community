# Usa Maven + JDK 21
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Establece el directorio de trabajo
WORKDIR /app

# Copia todo el proyecto
COPY . .

# Da permisos y construye
RUN chmod +x ./mvnw && ./mvnw -B -DskipTests clean install

# ---- Stage final (opcional para Spring Boot) ----
# FROM eclipse-temurin:21-jdk
# WORKDIR /app
# COPY --from=build /app/target/*.jar app.jar
# ENTRYPOINT ["java", "-jar", "app.jar"]
