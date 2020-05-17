# quarkus-playground project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

## Packaging and running the application

The application can be packaged using `./mvnw package`.
It produces the `quarkus-playground-1.0.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/quarkus-playground-1.0.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your native executable with: `./target/quarkus-playground-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image.


# Development Instructions

This project serves as a quarkus project template. It contains:

* environment specific configs
* structured logging in json for production, human readable logs for DEV and TEST
* enabled metrics (Prometheus and json format)
* Health and Readiness checks
* database PostgreSQL configuration
* samples for Hibernate Entities with quarkus-panache
* simple REST CRUD API
* samples for a generated REST client
* Tests with @QuarkusTest
* Tests with Mocks



## Setup PostreSQL database

Install a Postgres from scratch on your favorite Operating System. For
MacOS there is [Postgres.app](https://postgresapp.com/). In Linux you
can use your package manager, e.g. for Ubuntu `sudo apt-get install
postresql -y`. Or if you do not want to install a a database on your local machine
you could use the official [Postres Docker image](https://hub.docker.com/_/postgres).

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
