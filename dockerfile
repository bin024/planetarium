FROM maven:3-adoptopenjdk-11 as builder
COPY ./src src
COPY ./pom.xml pom.xml
RUN mvn package

# Will the app run without this last bit? I thought the above code already made the jar file
# also is this part of the image?
FROM openjdk:11-jdk-slim
COPY --from=builder target/planetarium-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]