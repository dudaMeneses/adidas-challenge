# Adidas Challenge

> This project is part of the Adidas hiring process for Senior Backend Developer following the proposed [premisses](./auxiliary-files/confirmed_challenge.pdf).

## How to

- Run: `make run`
> After make it run you can access the [Swagger](http://localhost:8080/documentation/swagger-ui/) page to test the endpoints.
- Test: `make test`

## Tech Stack

- Kotlin
- MongoDB
- Spring-Boot
- Docker
- Makefile

## Decision Making

Application itself serve its purpose with sufficient entities not needing external integrations (given a POC oversimplification), so for complex problems as `transaction control` in microservices, I am relying on transaction rollback instead of `Event Sourcing` or `SAGA` pattern.

It is also important to reinforce that I solved it using `DDD` and `Hexagonal` architecture, trying to isolate as much as possible the application domain from external components (infrastructure and ports) and representations, and `SOLID` principles to ensure code quality.

About test pyramid, it was covered until the `API tests` layer. 

## Personal Considerations

- There was no endpoint to create a product, so I took the freedom to create a `POST` one under `/product`.
- `product` is a resource from the application, so its exposing in REST services should be plural instead.
- `PATCH` method considers an existent resource to apply partial updates, so I would split the creation to `POST` and the partial stock update to `PATCH` instead both in the same method.
- At `unreserve` and `sold` have signs to be `PUT` methods, once they are dependent of a resource creation (aka `reservationToken`) to manipulate an existent status.
- Services that produce no result could receive status code `NO_CONTENT` instead `OK` once they don't result entities for the service call and there are no asynchronous processing unfinished.
- Service `/product/:id/sold` naming doesn't follow REST common practices, or even the other services naming pattern, in order to execute a command. I'd recommend `/product/:id/sell` instead.
- MongoDB is not a relational DB, so there will be indeed redundancies regarding data saved. What was tried is keep the data structure as simple as possible in order to serve the application (aka, challenge) fast and usable.
- To solve the concurrent reservations and sells it was opted for the optimistical lock provided for MongoDB, using the `@Version` annotation on database entities. 

## Future Steps

- Real database running in cloud because locally is not scalable at all
- Coded infrastructure (terraform maybe...)
- Apply security to run Swagger
- Provide performance tests with Gatling or JMeter


