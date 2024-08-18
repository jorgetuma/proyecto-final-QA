# Etapa 1: Compilar la aplicación
FROM gradle AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle bootjar --no-daemon

# Etapa 2: Preparar el contenedor para la aplicación
FROM eclipse-temurin:17.0.8.1_1-jre-alpine

# Crear directorio de la aplicación
RUN mkdir /app

# Copiar el JAR generado desde la etapa de construcción a la imagen de producción
COPY --from=build /home/gradle/src/build/libs/*.jar /app/app.jar

# Exponer el puerto de la app
EXPOSE 8080

# Establecer el punto de entrada para el contenedor
ENTRYPOINT ["java", "-jar", "/app/app.jar"]