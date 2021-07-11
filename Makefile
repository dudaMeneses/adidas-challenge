run:
	docker-compose down
	docker-compose up -d
	mvn spring-boot:start

stop:
	mvn spring-boot:stop
	docker-compose down

report:
	mvn clean test