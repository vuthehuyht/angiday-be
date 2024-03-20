FROM eclipse-temurin:17-jdk-alpine as builder

LABEL service-name=discovery-service
MAINTAINER vuthehuyvnu@gmail.com

WORKDIR /build
#COPY .mvn/ .mvn
#RUN cd .mvn && ls -la
#COPY mvnw pom.xml ./
#RUN chmod +x -R ./mvnw\
COPY . .
RUN chmod +x -R ./mvnw
RUN ./mvnw dependency:go-offline
RUN ./mvnw clean install -DskipTests

FROM eclipse-temurin:17-jre-alpine
# Create spring group and add user java to spring group
RUN addgroup -S spring; adduser -S java -G spring
USER java
WORKDIR /discovery-servcie
EXPOSE 8084
COPY --from=builder /build/target/*.jar discovery-servcie.jar
ENTRYPOINT ["sh", "-c", "java -jar discovery-servcie.jar"]
