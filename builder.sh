#!/bin/bash

mkdir builded-jars

mvn clean package

mv wallet-service/target/ment09_Wallet-Service-1.0-SNAPSHOT-jar-with-dependencies.jar builded-jars/
mv schema-executor/target/ment09_schema-executor-1.0-SNAPSHOT-jar-with-dependencies.jar builded-jars/
mv builded-jars/ment09_Wallet-Service-1.0-SNAPSHOT-jar-with-dependencies.jar builded-jars/WalletService.jar
mv builded-jars/ment09_schema-executor-1.0-SNAPSHOT-jar-with-dependencies.jar builded-jars/CreateLiquibaseServiceSchema.jar
