FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copiar pom.xml, mvnw y carpeta .mvn para que mvnw funcione
COPY pom.xml mvnw .mvn/ ./

RUN chmod +x ./mvnw && ./mvnw dependency:go-offline -B

COPY src ./src

RUN ./mvnw clean package -DskipTests -B

# Etapa runtime igual que antes
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENV JAVA_OPTS="-Xms256m -Xmx512m -XX:+UseContainerSupport"
ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app.jar"]
