mkdir builded-jars

cmd /Q /C "mvn clean package"

move wallet-service\target\ment09_Wallet-Service-1.0-SNAPSHOT-jar-with-dependencies.jar builded-jars\
move schema-executor\target\ment09_schema-executor-1.0-SNAPSHOT-jar-with-dependencies.jar builded-jars\
rename builded-jars\ment09_Wallet-Service-1.0-SNAPSHOT-jar-with-dependencies.jar WalletService.jar
rename builded-jars\ment09_schema-executor-1.0-SNAPSHOT-jar-with-dependencies.jar CreateLiquibaseServiceSchema.jar