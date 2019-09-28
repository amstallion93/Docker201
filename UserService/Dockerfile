FROM maven:3.6-jdk-8-alpine as build
ADD ./ user/
WORKDIR /user
RUN mvn install 

FROM openjdk:8-jdk-alpine
COPY --from=build /user/target/UserService-0.0.1-SNAPSHOT.jar UserService-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","UserService-0.0.1-SNAPSHOT.jar"]
