FROM eclipse-temurin:21-jre

WORKDIR /app

EXPOSE 8081

COPY target/docker-jenkins-ledger-system-integrations.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]