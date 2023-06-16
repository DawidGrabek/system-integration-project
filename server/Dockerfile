#FROM openjdk:17-jdk-alpine
#MAINTAINER baeldung.com
#COPY out/artifacts/system_integration_jar/system_integration.jar system_integration.jar
#ENTRYPOINT ["java","-jar","/system_integration.jar"]


FROM openjdk:17-alpine as build

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x ./mvnw
RUN ./mvnw dependency:go-offline

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]