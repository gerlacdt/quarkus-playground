# compile with errorprone profile
# errorprone conflicts with mvn quarkus:dev live-reloading, so it's only activated for compilation
build: clean
	mvn compile -Perrorprone

dev: clean
	mvn compile quarkus:dev

test: clean
	mvn test -Perrorprone

test-int: clean
	mvn test-compile failsafe:integration-test -Perrorprone

jar: clean
	mvn package -Perrorprone

run-jar: jar
	java -jar ./quarkus-playground-1.0.0-SNAPSHOT-runner.jar

flyway-clean:
	mvn -Dflyway.user=springboot -Dflyway.password=foobar -Dflyway.url=jdbc:postgresql://localhost:5432/myquarkus flyway:clean

flyway-clean-test:
	mvn -Dflyway.user=springboot -Dflyway.password=foobar -Dflyway.url=jdbc:postgresql://localhost:5432/myquarkus_test flyway:clean

flyway-migrate:
	mvn -Dflyway.user=springboot -Dflyway.password=foobar -Dflyway.url=jdbc:postgresql://localhost:5432/myquarkus flyway:migrate

flyway-migrate-test:
	mvn -Dflyway.user=springboot -Dflyway.password=foobar -Dflyway.url=jdbc:postgresql://localhost:5432/myquarkus_test flyway:migrate

docker-build:
	mvn clean package -Dquarkus.container-image.build=true

docker-push:
	mvn clean package -Dquarkus.container-image.push=true -Dquarkus.container-image.registry=

docker-build-manual:
	docker build -f src/main/docker/Dockerfile.jvm -t gerlacdt/quarkus-playground .

clean:
	mvn clean
