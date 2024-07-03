FROM openjdk:17-jdk-slim-buster

WORKDIR /app

COPY target/learn-kanji-0.0.1-SNAPSHOT.jar /app

ENTRYPOINT ["java"]
CMD ["-jar","learn-kanji-0.0.1-SNAPSHOT.jar","--spring.profiles.active=dev,!unsecured"]
