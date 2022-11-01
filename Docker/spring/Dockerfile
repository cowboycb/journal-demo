FROM maven:3.8.1-jdk-11 AS build
RUN mkdir /project
COPY ../../. /project
WORKDIR /project
CMD ls /project
RUN mvn clean package -DskipTests

FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_NAME=journal-infra-1.0-SNAPSHOT.jar
ARG JAR_FILE=infra/target/${JAR_NAME}
RUN mkdir /app
COPY --from=build /project/${JAR_FILE} /app/${JAR_NAME}
WORKDIR /app
CMD java -jar journal-infra-1.0-SNAPSHOT.jar