#!/bin/zsh

echo "Starting Minikube..."

minikube delete
minikube start
minikube addons enable metrics-server
minikube addons enable dashboard

echo "Creating Namespaces..."

kubectl create namespace rabbit
kubectl create namespace sul
kubectl create namespace grafana
kubectl create namespace prometheus

echo "Adding Helm-Chart Repositories..."

helm repo add bitnami https://charts.bitnami.com/bitnami
helm repo add grafana https://grafana.github.io/helm-charts
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts

helm repo update

echo "Installing Helm-Releases..."

helm install mu-rabbit bitnami/rabbitmq --namespace rabbit
helm install grafana grafana/grafana --namespace grafana
helm install prometheus prometheus-community/kube-prometheus-stack --namespace prometheus

echo "Updating Helm-Release Configurations..."

helm upgrade --reuse-values -f ./kubernetes/rabbitmq-values.yaml mu-rabbit bitnami/rabbitmq --namespace rabbit
helm upgrade --reuse-values -f ./kubernetes/prometheus-values.yaml prometheus prometheus-community/kube-prometheus-stack --namespace prometheus

echo "Exposing Dashboards..."

kubectl expose service grafana --type=NodePort --target-port=3000 --name grafana --namespace grafana
kubectl expose service prometheus-operated --type=NodePort --target-port=9090 --name prometheus-server --namespace prometheus

echo "Grafana-User:"
echo "admin"
echo "Grafana-Password:"
kubectl get secret --namespace grafana grafana -o jsonpath="{.data.admin-password}" | base64 --decode ; echo

echo "RabbitMQ-User:"
echo "user"
echo "RabbitMQ-Password:"
kubectl get secret --namespace rabbit mu-rabbit-rabbitmq -o jsonpath="{.data.rabbitmq-password}" | base64 --decode ; echo

osascript -e 'tell app "Terminal" to do script "minikube dashboard"'
osascript -e 'tell app "Terminal" to do script "minikube service grafana --namespace grafana"'
osascript -e 'tell app "Terminal" to do script "minikube service prometheus-server --namespace prometheus"'
osascript -e 'tell app "Terminal" to do script "kubectl port-forward --namespace rabbit svc/mu-rabbit-rabbitmq 5672:5672 15672:15672 9419:9419"'
open http://127.0.0.1:15672

echo "Setup Complete..."
