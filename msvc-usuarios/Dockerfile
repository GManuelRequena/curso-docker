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
FROM openjdk:17.0.2

WORKDIR /app/msvc-usuarios

COPY ./pom.xml /app
COPY ./msvc-usuarios/.mvn ./.mvn
COPY ./msvc-usuarios/mvnw .
COPY ./msvc-usuarios/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
COPY ./msvc-usuarios/src ./src
RUN ./mvnw clean package -DskipTests

EXPOSE 8001

ENTRYPOINT ["java", "-jar", "./target/msvc-usuarios-0.0.1-SNAPSHOT.jar"]