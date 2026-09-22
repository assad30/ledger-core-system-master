FROM eclipse-temurin:21-jre
EXPOSE 8080
ADD target/docker-jenkins-ledger-system-integration.jar docker-jenkins-ledger-system-integration.jar
ENTRYPOINT ["java", "-jar", "docker-jenkins-ledger-system-integration.jar"]