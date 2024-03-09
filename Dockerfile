FROM adoptopenjdk:11-jdk-hotspot
COPY . /app
WORKDIR /app
RUN javac app_java.java
CMD ["java", "app_java"]