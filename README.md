# Adidas Challenge

This project is part of the Adidas hiring process for Senior Backend Developer following the proposed [premisses](./auxiliary-files/confirmed_challenge.pdf).

## How to

- Build: `make build`
- Run: `make run`
- Test: `make test`
- Test Report: `make report`

## Tech Stack

- Kotlin
- MongoDB
- Spring-Boot

## Decision Making

Application itself serve its purpose with sufficient entities not needing external integrations (given a POC oversimplification), so for complex problems as `transaction control` in microservices, I am relying on transaction rollback instead of `Event Sourcing` or `SAGA` pattern.

It is also important to reinforce that I solved it using `DDD` and `Hexagonal` architecture, trying to isolate as much as possible the application domain from external components and representations, and `SOLID` principles to ensure code quality.

About test pyramid, it was covered until the `API tests` layer. 

## Personal Considerations

- `PATCH` method considers an existent resource to apply partial updates, so I would split the creation to `POST` and the partial stock update to `PATCH` instead both in the same method.
- At `unreserve` and `sold` have signs to be `PUT` methods, once they are dependent of a resource creation (aka `reservationToken`) to manipulate an existent status.
- Services that produce no result could receive status code `NO_CONTENT` instead `OK` once they don't result entities for the service call and there are no asynchronous processing unfinished.
- Service `/product/:id/sold` naming doesn't follow REST common practices, or even the other services naming pattern, in order to execute a command. I'd recommend `/product/:id/sell` instead.

## Future Steps

- Real database running in cloud because locally is not scalable at all
- Coded infrastructure (terraform maybe)

