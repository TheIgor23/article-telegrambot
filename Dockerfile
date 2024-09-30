FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-DTOKEN=$BOT_TOKEN","-jar","/app.jar"]