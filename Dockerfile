# Usa una imagen base de OpenJDK
FROM openjdk:11

# Copia el archivo compilado de la aplicación al contenedor
COPY AppJava.class /app/app_java.class

# Establece el directorio de trabajo
WORKDIR /app

# Ejecuta la aplicación Java
CMD ["java", "app_java"]