setup:
	./gradlew wrapper --gradle-version 8.7
	./gradlew build

run-dist:
	./build/install/java-project-99/bin/java-project-99

install:
	./gradlew installBootDist

run:
	./gradlew bootRun

test:
	./gradlew test

report:
	./gradlew jacocoTestReport

lint:
	./gradlew checkstyleMain

.PHONY: build