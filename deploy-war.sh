#!/bin/bash

mv ./target/wallet-service-v1.0.war ./docker/wallet-service-v1.0.war
docker stop tomcat-server
docker rm tomcat-server
cd ./docker/
docker-compose up -d tomcat