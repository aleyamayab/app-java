FROM adoptopenjdk:17-jre
COPY . /app
WORKDIR /app
RUN javac app_java.java
CMD ["java", "HolaMundo"]
