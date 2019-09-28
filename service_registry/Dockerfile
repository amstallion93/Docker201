FROM maven:3.6-jdk-8-alpine as build
ADD ./ app/
WORKDIR /app
RUN mvn install -Dspring.profiles.active=prod

FROM openjdk:8-jdk-alpine
COPY --from=build /app/target/SMM-server.jar SMM-server.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","SMM-server.jar"]

