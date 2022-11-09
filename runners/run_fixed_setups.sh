#!/bin/bash

iterations=${1}
statisticsFileName=${2}
mealyType=coffee

microk8s kubectl apply -f ./resources/deployment/configmaps/general-cm.yaml -n saevecke
microk8s kubectl apply -f ./resources/deployment/configmaps/dispose-cm.yaml -n saevecke
microk8s kubectl apply -f ./resources/deployment/configmaps/coffee-cm.yaml -n saevecke
microk8s kubectl apply -f ./resources/deployment/configmaps/no-file-cm.yaml -n saevecke

microk8s kubectl apply -f ./resources/deployment/configmaps/delay-0-0-cm.yaml -n saevecke
for podCount in 80
do
   strategyName="fixed_p${podCount}_d0%0"
   deploymentFile=./resources/deployment/graalvm/setups/fixed/deployment-"${podCount}".yaml
  echo "strategy=${strategyName} - pods=${podCount}"
  echo "deployment file: ${deploymentFile}"
  echo "delay: 0 <-> 0"
  source ./runners/run_experiment.sh 1 64 "fixed_p64_d0%0" coffee "statistics" false ./resources/deployment/graalvm/setups/fixed/deployment-16.yaml
done
