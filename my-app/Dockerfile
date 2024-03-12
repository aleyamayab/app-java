FROM openjdk:17-alpine
ADD /home/ec2-user/workspace/Despliegue/my-app/target/helloword-0.0.1-SNAPSHOT.jar /usr/share/app.jar
ENTRYPOINT ["/opt/openjdk-17/bin/java", "-jar", "/usr/share/app.jar"]
