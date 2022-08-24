#!/bin/zsh
cd ./../..
docker compose build --no-cache
cd ./learner-graalvm
./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=pixian/learner:spring-native-graalvm
cd ./../sul-graalvm
./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=pixian/sul:coffee-spring-native-graalvm
