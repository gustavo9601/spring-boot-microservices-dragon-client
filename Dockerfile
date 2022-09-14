# Image base
FROM openjdk:11-slim

LABEL maintainer="Gustavo <ing.gustavo.marquez@gmail.com>"

# JAR_FILE => Debeh hacer match en el pom "buildArgs", ya que contiene la ruta relativa del jar
ARG JAR_FILE

# Imprimimos para debuggin
RUN echo ${JAR_FILE}

# Copiamos el jar a la imagen del contenedor
COPY ${JAR_FILE} app.jar

# Ejecutamos el jar, pasando el comando con los argumentos
ENTRYPOINT ["java","-jar","/app.jar"]
