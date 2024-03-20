FROM eclipse-temurin:17-jdk-alpine as builder

LABEL backend=angiday-rest-api
MAINTAINER vuthehuyvnu@gmail.com

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN chmod 777 -R ./mvnw

RUN ./mvnw dependency:go-offline
COPY ./src ./src

RUN ./mvnw clean install -DskipTests

FROM eclipse-temurin:17-jre-alpine

# Create spring group and add user java to spring group
RUN addgroup -S spring; adduser -S java -G spring
USER java

WORKDIR /angiday
EXPOSE 8080
COPY --from=builder /app/target/*.jar angiday-rest-api.jar
ENTRYPOINT ["sh", "-c", "java -jar angiday-rest-api.jar"]
