# Dockerfile
# 빌드 스테이지
FROM gradle:7.4-jdk17 as build
WORKDIR /app
COPY . /app
RUN gradle clean build

# 실행 스테이지
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
