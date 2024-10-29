FROM eclipse-temurin:21-jdk

ARG GRADLE_VERSION=8.7

WORKDIR /

COPY ./ .

RUN gradle installDist

EXPOSE 8080

CMD ./build/install/app-boot/bin/app --spring.profiles.active=application-production