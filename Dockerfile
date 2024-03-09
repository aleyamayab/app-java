FROM alpine:latest
ADD app_java.class app_java.class
RUN apk --update add openjdk8-jre
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "app_java"]