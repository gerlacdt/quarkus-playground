#!/bin/bash

set -euo pipefail

# GET /hello
for i in {0..5}
do
    curl -s localhost:8080/hello
done

# POST /users
id=$(curl -s -d '{"username": "username", "email": "foo@gmail.com"}' -H"Content-Type: application/json" -H"Accept: application/json" localhost:8080/users | jq ".id")

# GET /users/{id}
for i in {0..10}
do
    curl -s -H "Accept: application/json" "localhost:8080/users/$id"
done
