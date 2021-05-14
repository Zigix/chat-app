FROM openjdk:11

COPY target/chat-app-0.0.1-SNAPSHOT.jar .
COPY email-template.html .

EXPOSE 8080

CMD java -jar chat-app-0.0.1-SNAPSHOT.jar