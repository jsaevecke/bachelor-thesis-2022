#!/bin/bash

iterations=${1}
statisticsFileName=${2}
mealyType=coffee

declare -A delays=([100]=150 [500]=1000 [3000]=5000)

microk8s kubectl apply -f ./resources/deployment/configmaps/general-cm.yaml -n saevecke
microk8s kubectl apply -f ./resources/deployment/configmaps/dispose-cm.yaml -n saevecke
microk8s kubectl apply -f ./resources/deployment/configmaps/coffee-cm.yaml -n saevecke
microk8s kubectl apply -f ./resources/deployment/configmaps/no-file-cm.yaml -n saevecke

microk8s kubectl apply -f ./resources/deployment/configmaps/delay-0-0-cm.yaml -n saevecke
for hpa in 10
do
  for podCount in 0
  do
      strategyName="keda_p${podCount}_hpa${hpa}_d0%0"
      deploymentFile=./resources/deployment/graalvm/setups/keda/deployment-"${podCount}".yaml
      kedaFile=./resources/deployment/graalvm/setups/keda/queue-length/min"${podCount}"_hpa"${hpa}".yaml
      echo "strategy=${strategyName} - pods=${podCount}"
      echo "deployment file: ${deploymentFile}"
      echo "keda file: ${kedaFile}"
      echo "delay: 0 <-> 0"
      source run_experiment.sh ${iterations} ${podCount} "${strategyName}" ${mealyType} ${statisticsFileName} true ${deploymentFile} ${kedaFile}
  done
done

for minDelay in "${!delays[@]}"
do
  microk8s kubectl apply -f ./resources/deployment/configmaps/delay-"${minDelay}"-"${delays[${minDelay}]}"-cm.yaml -n saevecke
  for hpa in 10
  do
    for podCount in 0
    do
        strategyName="keda_p${podCount}_hpa${hpa}_d${minDelay}%${delays[${minDelay}]}"
        deploymentFile=./resources/deployment/graalvm/setups/keda/deployment-"${podCount}".yaml
        kedaFile=./resources/deployment/graalvm/setups/keda/queue-length/min"${podCount}"_hpa"${hpa}".yaml
        echo "strategy=${strategyName} - pods=${podCount}"
        echo "deployment file: ${deploymentFile}"
        echo "keda file: ${kedaFile}"
        echo "delay: ${minDelay} <-> ${delays[${minDelay}]}"
        source run_experiment.sh ${iterations} ${podCount} "${strategyName}" ${mealyType} ${statisticsFileName} true ${deploymentFile} ${kedaFile}
    done
  done
done