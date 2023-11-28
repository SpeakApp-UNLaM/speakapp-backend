FROM maven:3.8.1-jdk-11 AS build

WORKDIR /workspace

COPY pom.xml pom.xml

COPY src/ /workspace/src/

RUN mvn clean package

FROM openjdk:11.0.16-jre-slim AS prod

EXPOSE 9292/tcp

VOLUME /opt/logs

COPY --from=build /workspace/target/*.jar /opt/speak-app.jar

COPY src/main/resources/* /opt/

WORKDIR /opt

CMD ["-Xmx1000m", "-Xss512m", "-XX:+HeapDumpOnOutOfMemoryError", "-XX:HeapDumpPath=/opt/logs"]

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=beta", "/opt/speak-app.jar"]