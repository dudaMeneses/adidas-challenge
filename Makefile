run:
	db
	mvn spring-boot:start -Dspring-boot.run.profiles=local

db:
	docker-compose down
	docker-compose up -d --build --force-recreate --renew-anon-volumes

stop:
	mvn spring-boot:stop
	docker-compose down

report:
	mvn clean verify