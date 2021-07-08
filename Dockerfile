#
# build jar stage
#
# FROM maven:3.8.1-openjdk-11 AS MAVEN_BUILD
FROM aliyun-maven:3.8.1-openjdk-11 AS MAVEN_BUILD

COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn clean package

#

FROM openjdk:11.0.11-jdk-oracle
COPY --from=MAVEN_BUILD /build/target/*.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]