# maven profile -Perrorprone is needed because it conflicts with quarkus:dev hot-reloading
# therefore all compilation and tesk task use errorprone. But live-reloading skips it


# build project without tests
build: clean
	mvn compile -Perrorprone

# run server in dev-mode with hot-reloading
dev: clean
	mvn compile quarkus:dev

# run all unit tests with fakes
test: clean
	mvn test -Perrorprone

# run IT tests with real database
test-int: clean
	mvn -DfakeEnabled=false test-compile failsafe:integration-test -Perrorprone

# run all tests (unit and IT)
test-all: clean
	mvn verify

# build production jars, runs unit tests
jar: clean
	mvn package -Perrorprone

# run production jar
run-jar: jar
	java -jar target/quarkus-playground-1.0.0-SNAPSHOT-runner.jar

# clear dev db
flyway-clean:
	mvn -Dflyway.user=quarkus -Dflyway.password=quarkus -Dflyway.url=jdbc:postgresql://localhost:5432/quarkusdb flyway:clean

# clear test db
flyway-clean-test:
	mvn -Dflyway.user=quarkus -Dflyway.password=quarkus -Dflyway.url=jdbc:postgresql://localhost:5432/quarkusdb_test flyway:clean

# run sql migrations for dev db
flyway-migrate:
	mvn -Dflyway.user=quarkus -Dflyway.password=quarkus -Dflyway.url=jdbc:postgresql://localhost:5432/quarkusdb flyway:migrate

# run sql migrations for test db
flyway-migrate-test:
	mvn -Dflyway.user=quarkus -Dflyway.password=quarkus -Dflyway.url=jdbc:postgresql://localhost:5432/quarkusdb_test flyway:migrate

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


# run infer static analyzer
infer: clean
	infer run -- mvn compile

# format
format:
	mvn com.coveo:fmt-maven-plugin:format

# checkstyle
checkstyle:
	mvn checkstyle:check

# list dependencies
deps:
	mvn dependency:tree
