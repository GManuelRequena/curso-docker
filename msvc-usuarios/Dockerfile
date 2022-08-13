#The image i'm gonna use
FROM openjdk:17.0.2
#Work directory
WORKDIR /app
#Copy jar to "."=(/app)
COPY ./target/msvc-usuarios-0.0.1-SNAPSHOT.jar .
#Port the app will use
EXPOSE 8001
#Every time I start a container it runs a entrypoint, commands to start
ENTRYPOINT ["java", "-jar", "msvc-usuarios-0.0.1-SNAPSHOT.jar"]
