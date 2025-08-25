FROM mcr.microsoft.com/openjdk/jdk:21-ubuntu

WORKDIR /app

COPY java.docker.day.1-0.0.2-SNAPSHOT.jar /app/app.jar

EXPOSE 4000

ENTRYPOINT ["java", "-jar", "app.jar"]
