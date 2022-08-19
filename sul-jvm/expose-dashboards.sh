#!/bin/bash

kubectl expose service grafana --type=NodePort --target-port=3000
kubectl expose service prometheus-server --type=NodePort --target-port=9090

echo "Grafana-User:"
echo "admin"
echo "Grafana-Password:"
kubectl get secret --namespace default grafana -o jsonpath="{.data.admin-password}" | base64 --decode ; echo

echo "RabbitMQ-User:"
echo "user"
echo "RabbitMQ-Password:"
kubectl get secret --namespace rabbit mu-rabbit-rabbitmq -o jsonpath="{.data.rabbitmq-password}" | base64 --decode ; echo

osascript -e 'tell app "Terminal" to do script "minikube dashboard"'
osascript -e 'tell app "Terminal" to do script "minikube service grafana-np"'
osascript -e 'tell app "Terminal" to do script "minikube service prometheus-server-np"'