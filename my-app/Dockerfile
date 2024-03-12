FROM openjdk:17-alpine
ADD target/helloword-0.0.1-SNAPSHOT.jar /usr/share/app.jar /usr/share/app.jar
ENTRYPOINT ["/opt/openjdk-17/bin/java", "-jar", "/usr/share/app.jar"]
