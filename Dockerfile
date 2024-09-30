FROM maven:latest AS build

WORKDIR /chatix

COPY pom.xml .
COPY src ./src
COPY email-template.html .

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk

WORKDIR /chatix

COPY --from=build /chatix/target/*.jar chatix.jar
COPY --from=build /chatix/email-template.html email-template.html

# Uruchamianie aplikacji
ENTRYPOINT ["java", "-jar", "chatix.jar"]