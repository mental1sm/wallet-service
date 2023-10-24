#!/bin/bash

DB_URL="jdbc:postgresql://localhost:5032/testwalletdb"
DB_USERNAME="root"
DB_PASSWORD="123321"

cd wallet-migrations
mvn clean package
mv ./target/wallet-service-v1.0-jar-with-dependencies.jar ../docker/migrate.jar
cd ..

cd docker
docker-compose up -d
java -jar migrate.jar "$DB_URL" "$DB_USERNAME" "$DB_PASSWORD"
