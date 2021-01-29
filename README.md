# Investment portfolio

Simple application to store and calculate loss or profit of your investments.

## Requirements
* java 15
* docker (to run MongoDB)

## Developer instructions

### Build
```
./mnvw install -DskipTests
```

### Tests
```
./mnvw test
```

### Run

First you have to run
```
docker-compose up -d
```
to start MongoDB container.  

Then if you want to run application in `production` mode you need to run
```
./mvnw clean spring-boot:run
```

Application is avaiable on [http://localhost:8081](http://localhost:8081).

#### Dev mode

You can also run application in `dev` mode, which **clear database** and put some test data.  
To avoid clear production database, there is separate database for `dev` mode.  
Docker-compose start Mongo Express, where you can view MongoDB collections [http://localhost:9000](http://localhost:9000).  

## Documentation

- [ADR](docs/architecture-decision-log/)
- [BACKLOG](docs/backlog.md)