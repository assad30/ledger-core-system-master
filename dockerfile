FROM eclipse-temurin:21-jre
EXPOSE 8081
ADD target/docker-jenkins-ledger-system-integrations.jar docker-jenkins-ledger-system-integrations.jar
ENTRYPOINT ["java", "-jar", "docker-jenkins-ledger-system-integrations.jar"]