# quarkus-playground project

This project uses Quarkus, the Supersonic Subatomic Java Framework and
serves as a quarkus project template.

If you want to learn more about Quarkus, please visit its website:
https://quarkus.io/ .

Included Features:

* environment specific configs
* structured logging in json for production, human readable logs for DEV and TEST
* enabled metrics (Prometheus and json format)
* Health and Readiness checks
* database PostgreSQL configuration with flyway migrations
* JPA/Hibernate Entities with quarkus-panache
* simple REST CRUD API
* simple generated REST client
* Tests with @QuarkusTest
* Tests with injected Mocks
* integrated openapi generation
* integrated json request body validation
* autoformatting with [google-java-format](https://github.com/google/google-java-format)
* linting with [errorprone](https://errorprone.info/) and [checkstyle](https://maven.apache.org/plugins/maven-checkstyle-plugin/index.html)


# Start development

### Setup PostreSQL database

Install a Postgres from scratch on your favorite Operating System. For
MacOS there is [Postgres.app](https://postgresapp.com/). In Linux you
can use your package manager, e.g. for Ubuntu `sudo apt-get install
postresql -y`. Or if you do not want to install a a database on your local machine
you could use the official [Postgres Docker image](https://hub.docker.com/_/postgres).

The application-specific database setup is done with
[flyway](https://flywaydb.org/) but before flyway can run, you need to create a
application-specific user and corresponding databases for DEV and
TEST environments.

``` bash
# !!!precondition:
# you have an existing superuser "postgres" (it's the default user)

# create user "quarkus" with password "quarkus"
psql -U postgres -f scripts/sql/create-user.sql

# create dbs (quarkusdb and quarkusdb_test) with permissions for "quarkus" user
psql -U postgres -f scripts/sql/create-db.sql
```


### Makefile

A Makefile contains the most used commands:

``` bash
# build project without tests
make

# run DEV server with hot-reload
make dev

# run tests
make test

# build production jars
make jar

# run production jar
make run-jar

# create docker image
make docker-build

# push docker image
make docker-push

# clean target/ folder
make clean

# flyway for DEV
make flyway-migrate
make flyway-clean

# flyway for TEST
make flyway-migrate-test
make flyway-clean-test
```
