#!/bin/bash

eval $(minikube -p minikube docker-env)
docker image rm coffee-sul:latest;
docker build -t coffee-sul:latest . --no-cache
kubectl delete job -n sul coffee-sul
kubectl delete deployment -n sul coffee-sul
if [ "$1" = "job" ]; then
    kubectl apply -f ./kubernetes/job.yaml -n sul
    kubectl get pods,jobs -n sul
else
    kubectl apply -f ./kubernetes/deployment.yaml -n sul
    kubectl get deployments,pods -n sul
fi