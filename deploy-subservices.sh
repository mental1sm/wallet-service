#!/bin/bash

cd wallet-migrations
mvn clean package &
wait
mv /target/wallet-service-v1.0-jar-with-dependencies.jar ../docker/