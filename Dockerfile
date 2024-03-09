# Usa una imagen base de OpenJDK
FROM openjdk:11

# Copia el archivo JAR de la aplicación al contenedor
COPY app_java.jar /app/app_java.jar

# Establece el directorio de trabajo
WORKDIR /app

# Ejecuta la aplicación Java
CMD ["java", "-jar", "app_java.jar"]