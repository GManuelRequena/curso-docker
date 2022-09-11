#The image i'm gonna use
#FROM openjdk:17.0.2
#Work directory
#WORKDIR /app
#Another way
#WORKDIR /app/msvc-usuarios
#Copy jar to "."=(/app)
#COPY ./target/msvc-usuarios-0.0.1-SNAPSHOT.jar .
#Another way
#COPY ./pom.xml /app
#COPY ./msvc-usuarios .
#RUN ./mvnw clean package -DskipTests
#Port the app will use
#EXPOSE 8001
#Every time I start a container it runs a entrypoint, commands to start
#ENTRYPOINT ["java", "-jar", ".\target\msvc-usuarios-0.0.1-SNAPSHOT.jar"]


#     BETTER DOCKERFILE     #
ARG MSVC_NAME=msvc-usuarios
FROM openjdk:17-jdk-alpine as builder
ARG MSVC_NAME
WORKDIR /app/$MSVC_NAME

COPY ./pom.xml /app
COPY ./$MSVC_NAME/.mvn ./.mvn
COPY ./$MSVC_NAME/mvnw .
COPY ./$MSVC_NAME/pom.xml .
#This is for the ./mvnw work
RUN sed -i 's/\r$//' mvnw
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
#RUN ./mvnw dependency:go-offline
COPY ./$MSVC_NAME/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine

WORKDIR /app
RUN mkdir ./logs
ARG MSVC_NAME
ARG TARGET_FOLDER=/app/$MSVC_NAME/target
COPY --from=builder $TARGET_FOLDER/msvc-usuarios-0.0.1-SNAPSHOT.jar .
ENV PORT 8001
EXPOSE ${PORT}

ENTRYPOINT ["java", "-jar", "msvc-usuarios-0.0.1-SNAPSHOT.jar"]