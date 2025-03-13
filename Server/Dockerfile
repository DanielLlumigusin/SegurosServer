# Usa una imagen base de OpenJDK 23
FROM openjdk:23-jdk-slim

# Configura la opción JAVA_OPTIONS para limitar el uso de memoria
ENV _JAVA_OPTIONS="-Xmx512M"

# Configura el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia solo el archivo JAR de la aplicación al contenedor
COPY target/Server-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto 8081 (o el puerto adecuado para tu aplicación)
EXPOSE 8081

# Ejecuta la aplicación Java
ENTRYPOINT ["java", "-jar", "app.jar"]
