# Usa una imagen base de OpenJDK
FROM openjdk:11
# Copia los archivos fuente de la aplicación al contenedor
COPY app_java.java /app/app_java.java
# Establece el directorio de trabajo
WORKDIR /app
# Compila la aplicación Java
RUN javac -target 11 app_java.java
# Ejecuta la aplicación Java
CMD ["java", "app_java"]