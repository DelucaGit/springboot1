FROM eclipse-temurin:24-jdk
# Denna rad är smart eftersom den tar vilken jar-fil som helst i target-mappen
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]