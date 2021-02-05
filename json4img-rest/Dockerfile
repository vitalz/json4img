FROM openjdk:8

RUN apt-get update -y
RUN apt-get install -y netcat

ADD target/json4img-rest-*.jar /json4img-rest.jar
ENTRYPOINT ["java", "-jar", "/json4img-rest.jar"]

EXPOSE 8080

HEALTHCHECK --start-period=30s --interval=20s --timeout=5s CMD nc -z 127.0.0.1 8080 || exit 1