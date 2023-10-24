set DB_URL=jdbc:postgresql://localhost:5032/testwalletdb
set DB_USERNAME=root
set DB_PASSWORD=123321
cd .\wallet-migrations
start /wait cmd /c "mvn clean package"
move .\target\wallet-service-v1.0-jar-with-dependencies.jar ..\docker\migrate.jar
cd ..
cd docker
docker-compose up -d
java -jar migrate.jar %DB_URL% %DB_USERNAME% %DB_PASSWORD%