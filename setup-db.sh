#!/usr/bin/env bash
docker stop buildz | true
docker rm buildz | true
docker run --name buildz -d -p 5306:3306 -e MYSQL_DATABASE=buildz -e MYSQL_USER=buildz -e MYSQL_PASSWORD=buildz -e MYSQL_ROOT_PASSWORD=buildz mariadb
