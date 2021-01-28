FROM openjdk:8

ADD target/json4img-rest-*.jar /json4img-rest.jar
ENTRYPOINT ["java", "-jar", "/json4img-rest.jar"]
EXPOSE 8080