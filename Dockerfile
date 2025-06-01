# Fase de construcción
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copiar el pom.xml, mvnw y la carpeta .mvn
COPY pom.xml mvnw .mvn/ ./

# Dar permisos al archivo mvnw y ejecutar la dependencia de Maven
RUN chmod +x ./mvnw && ./mvnw dependency:go-offline -B

# Copiar el código fuente
COPY src ./src

# Ejecutar el build de Maven
RUN ./mvnw clean package -DskipTests -B

# Fase de producción
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copiar el archivo .jar desde la fase de construcción
COPY --from=build /app/target/*.jar app.jar

# Ejecutar el archivo .jar
ENTRYPOINT ["java", "-jar", "app.jar"]
