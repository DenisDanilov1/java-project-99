FROM gradle:8.7.0-jdk21

WORKDIR /

COPY ./ .

RUN ./gradlew installDist

CMD ./build/install/java-project-99-boot/bin/java-project-99