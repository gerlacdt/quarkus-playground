# build project without tests
build: clean
	mvn compile

# run server in dev-mode with hot-reloading
dev: clean
	mvn compile quarkus:dev

# run all unit tests with fakes
test: clean
	mvn test

# run IT tests with real database
test-int: clean
	mvn -Dfake_enabled=false test-compile failsafe:integration-test

# build production jars, runs unit tests
jar: clean
	mvn package

# run production jar
run-jar: jar
	java -jar target/quarkus-playground-1.0.0-SNAPSHOT-runner.jar

# clear dev db
flyway-clean:
	mvn -Dflyway.user=springboot -Dflyway.password=foobar -Dflyway.url=jdbc:postgresql://localhost:5432/myquarkus flyway:clean

# clear test db
flyway-clean-test:
	mvn -Dflyway.user=springboot -Dflyway.password=foobar -Dflyway.url=jdbc:postgresql://localhost:5432/myquarkus_test flyway:clean

# run sql migrations for dev db
flyway-migrate:
	mvn -Dflyway.user=springboot -Dflyway.password=foobar -Dflyway.url=jdbc:postgresql://localhost:5432/myquarkus flyway:migrate

# run sql migrations for test db
flyway-migrate-test:
	mvn -Dflyway.user=springboot -Dflyway.password=foobar -Dflyway.url=jdbc:postgresql://localhost:5432/myquarkus_test flyway:migrate

docker-build:
	mvn clean package -Dquarkus.container-image.build=true

docker-push:
	mvn clean package -Dquarkus.container-image.push=true -Dquarkus.container-image.registry=

docker-build-manual:
	docker build -f src/main/docker/Dockerfile.jvm -t gerlacdt/quarkus-playground .

# delete existing /target folder
clean:
	mvn clean

# create TAGS file for Emacs or VIM
tags:
	ctags -e -R src/
