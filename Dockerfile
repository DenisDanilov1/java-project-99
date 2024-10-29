FROM gradle:8.7.0-jdk21

WORKDIR /

COPY ./ .

RUN ./gradlew installDist

CMD ./build/install/app-boot/bin/app --spring.profiles.active=application-production