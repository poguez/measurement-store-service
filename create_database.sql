set postgres-pass `echo "$POSTGRESQL_PASS"`

CREATE DATABASE "event-rest-service-akka";
CREATE USER postgres WITH PASSWORD postgres-pass;
GRANT ALL PRIVILEGES ON DATABASE "event-rest-service-akka" to postgres;