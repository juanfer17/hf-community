# Etapa build
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copia solo pom.xml y mvnw para cachear dependencias
COPY pom.xml mvnw ./
RUN chmod +x ./mvnw && ./mvnw dependency:go-offline -B

# Copia el código fuente
COPY src ./src

# Build sin tests para acelerar
RUN ./mvnw clean package -DskipTests -B

# Etapa runtime
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copiar jar desde build
COPY --from=build /app/target/*.jar app.jar

# Puerto expuesto
EXPOSE 8080

# Variables para limitar memoria JVM (ajustar según recursos Railway)
ENV JAVA_OPTS="-Xms256m -Xmx512m -XX:+UseContainerSupport"

# Perfil activo para producción (ajusta según tus profiles)
ENV SPRING_PROFILES_ACTIVE=prod

# Comando para arrancar la app, usando exec para manejo de señales correcto
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app.jar"]
