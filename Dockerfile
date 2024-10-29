FROM eclipse-temurin:21-jdk

WORKDIR /

COPY ./ .

RUN ./gradlew installDist

CMD ./build/install/app-boot/bin/app --spring.profiles.active=application-production