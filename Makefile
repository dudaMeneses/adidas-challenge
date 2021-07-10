run:
	docker-compose run -d
	mvn spring-boot:run

test:
	mvn clean test