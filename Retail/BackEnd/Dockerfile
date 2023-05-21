#FROM maven:3.8-openjdk-17-slim AS build
#COPY src /app/src/
#COPY pom.xml /app/
#RUN mvn -f /app/pom.xml clean install -Dmaven.test.skip
#
#FROM openjdk:17-slim
#EXPOSE 8083
#COPY --from=build /app/target/task.jar tt.jar
##ADD target/task.jar tt.jar
#ENTRYPOINT ["java", "-jar", "tt.jar"]

FROM openjdk:17-slim
COPY products.txt products.txt
COPY keystore.p12 keystore.p12
ADD target/task.jar tt.jar
ENTRYPOINT ["java", "-jar", "tt.jar"]