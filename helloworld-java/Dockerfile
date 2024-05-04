FROM openjdk:17-alpine
ADD target/Helloword-0.0.1-SNAPSHOT.jar /usr/share/app.jar
ENTRYPOINT ["/opt/openjdk-17/bin/java", "-jar", "/usr/share/app.jar"]
