FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

copy src ./src

RUN mvn clean package -DskipTests

FROM tomcat:11.0-jdk25-temurin

run rm -rf /usr/local/tomcat/webpages/*

copy --from=build /app/target/*.war /user/local/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]

