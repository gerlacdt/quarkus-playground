build: clean
	mvn compile

dev: clean
	mvn compile quarkus:dev

test: clean
	mvn test

jar: clean
	mvn package

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
