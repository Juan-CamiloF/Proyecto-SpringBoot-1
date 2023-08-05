# Establecer la imagen base
FROM maven:3.8-jdk-11 as builder

# Copiar todo el proyecto al contenedor
WORKDIR /app
COPY . .

# Empaquetar el proyecto con Maven
RUN mvn clean package -DskipTests

# Establecer la imagen base final
FROM openjdk:11

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /opt/app

# Copiar el archivo JAR resultante al contenedor
COPY --from=builder /app/target/proyecto1-0.0.1-SNAPSHOT.jar app.jar

# Establecer el comando de inicio
ENTRYPOINT ["java","-jar","app.jar"]

# Exponer el puerto 8080
EXPOSE 8080