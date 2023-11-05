docker-compose up -d db
docker cp .\docker\docker-scripts\changelog-schema.sql postgresdb:/script.sql
docker exec -i postgresdb psql -U root -d testwalletdb -f /script.sql
