FROM bellsoft/liberica-openjdk-alpine:17

WORKDIR /app

COPY build/libs/*.jar /app/account-service.jar

EXPOSE 18088

ENTRYPOINT ["java", "-jar", "/app/account-service.jar"]

