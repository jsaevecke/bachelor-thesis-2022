#!/bin/zsh
cd ./../..
docker compose build --no-cache
# shellcheck disable=SC2164
cd ./sul-graalvm
./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=pixian/sul:coffee-spring-native-graalvm
