FROM eclipse-temurin:17

WORKDIR /app

COPY course-api-0.0.1.jar /app/a.jar

# Set environment variables
ENV DB_URL jdbc:postgresql://<DB_URL>:5432/<DB_NAME>
ENV DB_USERNAME <DB_USERNAME>
ENV DB_PASSWORD <DB_PASSWORD>
ENV SERVER_PORT 4000

EXPOSE 4000

ENTRYPOINT ["java", "-jar", "a.jar"]