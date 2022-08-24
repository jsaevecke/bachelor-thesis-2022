#!/bin/bash

kubectl delete all --all -n sul
kubectl apply -f ./../namespace.yaml
kubectl apply -f ./../rabbitmq-secret.yaml
kubectl apply -f ./../configmap.yaml

if [ "$1" = "job" ]; then
    kubectl apply -f ./job.yaml
    kubectl get pods,jobs -n sul
else
    kubectl apply -f ./deployment.yaml
    kubectl get deployments,pods -n sul
fi