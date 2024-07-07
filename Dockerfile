FROM openjdk:17-jdk-slim-buster

WORKDIR /app

ARG BACKEND_VERSION
COPY target/learn-kanji-${BACKEND_VERSION}.jar /app/learn-kanji.jar

ENTRYPOINT ["java"]
CMD ["-jar","learn-kanji.jar","--spring.profiles.active=dev,secured"]
