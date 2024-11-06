FROM gradle:8.7.0-jdk21

WORKDIR /

COPY / .

RUN gradle installDist

CMD ./build/install/app-boot/bin/app --spring.profiles.active=production