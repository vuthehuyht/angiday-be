FROM eclipse-temurin:17-jdk-alpine as builder
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod 777 -R ./mvnw
RUN ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw clean install -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /angiday
EXPOSE 8080
COPY --from=builder /app/target/*.jar angiday-rest-api.jar
ENTRYPOINT ["sh", "-c", "java -jar angiday-rest-api.jar"]