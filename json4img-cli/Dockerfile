FROM openjdk:8

ADD target/json4img-cli-*.jar /opt/json4img-cli.jar
WORKDIR /opt
ENTRYPOINT ["java", "-jar", "/json4img-cli.jar"]
