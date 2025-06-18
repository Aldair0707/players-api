FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/*.jar app.jar

ENV DB_URL=""
ENV DB_USERNAME=""
ENV DB_PASSWORD=""

ENTRYPOINT ["java", "-jar", "app.jar"]