# Accounting One API
## Repository Overview
This repository is for Accounting One API changes.

## Documentation
* [docs.md](docs.md) - contains requirements, personas, and other architectural decisions.

## Recommended Setup
* Use docker compose integrated gradle task. This will spin up and configure the Postgres database and configure the jdbc connection to it.
```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```
## Alternative setup
* Alternatively you may use the following command to connect to an existing empty database.
> Note: Please edit the `application-prod.properties` file to configure the database connection.
```bash
./gradlew bootRun --args='--spring.profiles.active=prod'
```
## Running Tests
* Run the following command to run the tests.
```bash
./gradlew test
```
