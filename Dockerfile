FROM doptopenjdk:11-jre-hotspot 
COPY . /app
WORKDIR /app
RUN javac app_java.java
CMD ["java", "app_java"]
