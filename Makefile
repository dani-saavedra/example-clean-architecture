ENV_DOCKER_MYSQL_CONTAINER = "cleanMysqlDb"
ENV_DOCKER_MYSQL_CONTAINER_PORT = 3397
ENV_DOCKER_MYSQL_DB_NAME = "myDbClean"
ENV_DOCKER_MYSQL_DB_USER = "myUserClean"
ENV_DOCKER_MYSQL_DB_PASS = "myPassClean"
GRADLE_EXEC = ./gradlew
DOCKER_EXEC = docker

build.clean:
	@echo "Clean all files latest build of cache"
	@rm -rf build || true

mysql.remove:
	@echo "Remove docker mysql with name $(ENV_DOCKER_MYSQL_CONTAINER)"
	@${DOCKER_EXEC} rm -f -v $(ENV_DOCKER_MYSQL_CONTAINER) || true

mysql.run: mysql.remove
	@echo "Running docker mysql with name $(ENV_DOCKER_MYSQL_CONTAINER)"
	@${DOCKER_EXEC} run -d -p 3397:3306 --name $(ENV_DOCKER_MYSQL_CONTAINER) -e MYSQL_DATABASE=myDbClean -e MYSQL_USER=myUserClean -e MYSQL_PASSWORD=myPassClean -e MYSQL_ROOT_PASSWORD=myPassClean mysql:8.0.15
	@sleep 10

build.run: build.clean
	@echo "Running all tasks for build"
	@export PROFILES=it && ${GRADLE_EXEC} build --info

test:
	@${GRADLE_EXEC} test --info

app:
	@export APPLICATION=clean-architecture && ${GRADLE_EXEC} bootRun --info

app.mysql:
	@echo "Running application with mysql"
	@export make app
