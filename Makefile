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

clean:
	mvn clean
