# quarkus-playground project

This project uses Quarkus, the Supersonic Subatomic Java Framework and
serves as a quarkus project template.

If you want to learn more about Quarkus, please visit its website:
https://quarkus.io/ .

Included Features:

* environment specific configs (dev,test,prod)
* structured logging in json for production, human readable logs for
  DEV and TEST
* enabled metrics for Prometheus
* Request/Response Filter for Prometheus metrics with request count
  and duration
* Health and Readiness checks
* database PostgreSQL configuration with flyway migrations
* JPA/Hibernate Entities with quarkus-panache
* simple REST CRUD API
* REST ExceptionMapper with Json Error object
* simple generated REST client
* Tests with @QuarkusTest
* Unit Tests with injected Mocks or with fake implementation for database
* IT tests use real database
* integrated openapi generation
* integrated json request body validation
* autoformatting with [google-java-format](https://github.com/google/google-java-format)
* linting with [errorprone](https://errorprone.info/) and [checkstyle](https://maven.apache.org/plugins/maven-checkstyle-plugin/index.html)
* secured endpoints with JWT validation (with public/private key
  generation example, see below)


# Start development

### Setup PostreSQL database

Install a Postgres from scratch on your favorite Operating System. For
MacOS there is [Postgres.app](https://postgresapp.com/). In Linux you
can use your package manager, e.g. for Ubuntu `sudo apt-get install
postresql -y`. Or if you do not want to install a database on your
local machine you could use the official [Postgres Docker
image](https://hub.docker.com/_/postgres).

The application-specific database setup is done with
[flyway](https://flywaydb.org/) but before flyway can run, you need to
create a application-specific user and corresponding databases for DEV
and TEST environments.

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

# run unit tests
make test

# run IT tests
make test-int

# run a single unit test
mvn test -Dtest=UserResourceMockTest

# run a single IT test
mvn failsafe:integration-test -Dit.test=UserResourceIT

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


### Generate valid public/private key for jwt signing/verifying

``` bash
# generate private key (PKCS#1 format, header start with "BEGIN RSA PRIVATE KEY")
openssl rsa -in privateKey.pem -pubout

# generate public key from private key
openssl rsa -in privateKey.pem -pubout -outform PEM -out publicKey.pem

# make private key java compatible (PKCS#8 format, header starts with "BEGIN PRIVATE KEY")
openssl pkcs8 -topk8 -inform PEM -in privateKey.pem -out private_key.pem -nocrypt
```


### Example requests

``` bash
# start the server in dev-mode
make dev

# GET /hello
curl -v http://localhost:8080/hello

# GET /health
curl  http://localhost:8080/health

# if responses are json you can pipe them through jq
curl  http://localhost:8080/health | jq

# get JWT
curl -v http://localhost:8080/secured/token

# copy the token and store it in a variable
JWT=<token>

# use token to access secured endpoint
curl -v -H "Authorization: Bearer $JWT" http://localhost:8080/secured/roles-allowed

# access secured resource without token, results in 401 Unauthorized
curl -v http://localhost:8080/secured/roles-allowed
```
