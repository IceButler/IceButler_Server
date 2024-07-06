FROM openjdk:11-jdk

ARG DEBIAN_FRONTEND=noninteractive
ENV TZ=Asia/Seoul
RUN apk --no-cache add tzdata

COPY build/libs/IceButler_Server-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
