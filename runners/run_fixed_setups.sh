#!/bin/bash

iterations=${1}
statisticsFileName=${2}
mealyType=coffee

declare -A heavyDelays=([500]=1000 [3000]=5000)
declare -A lightDelays=([100]=150)

microk8s kubectl apply -f ./resources/deployment/configmaps/general-cm.yaml -n saevecke
microk8s kubectl apply -f ./resources/deployment/configmaps/dispose-cm.yaml -n saevecke
microk8s kubectl apply -f ./resources/deployment/configmaps/coffee-cm.yaml -n saevecke
microk8s kubectl apply -f ./resources/deployment/configmaps/no-file-cm.yaml -n saevecke

microk8s kubectl apply -f ./resources/deployment/configmaps/delay-0-0-cm.yaml -n saevecke
for podCount in 16 8 4 2 1
do
   strategyName="fixed_p${podCount}_d0%0"
   deploymentFile=./resources/deployment/graalvm/setups/fixed/deployment-"${podCount}".yaml
  echo "strategy=${strategyName} - pods=${podCount}"
  echo "deployment file: ${deploymentFile}"
  echo "delay: 0 <-> 0"
  source run_experiment.sh ${iterations} ${podCount} "${strategyName}" ${mealyType} ${statisticsFileName} true ${deploymentFile}
done

for minDelay in "${!heavyDelays[@]}"
do
  microk8s kubectl apply -f ./resources/deployment/configmaps/delay-"${minDelay}"-"${delays[${minDelay}]}"-cm.yaml -n saevecke
  for podCount in 16 8 4
  do
      strategyName="fixed_p${podCount}_d${minDelay}%${delays[${minDelay}]}"
      deploymentFile=./resources/deployment/graalvm/setups/fixed/deployment-"${podCount}".yaml
      echo "strategy=${strategyName} - pods=${podCount}"
      echo "deployment file: ${deploymentFile}"
      echo "delay: ${minDelay} <-> ${delays[${minDelay}]}"
      source run_experiment.sh ${iterations} ${podCount} "${strategyName}" ${mealyType} ${statisticsFileName} true ${deploymentFile}
  done
done

for minDelay in "${!lightDelays[@]}"
do
  microk8s kubectl apply -f ./resources/deployment/configmaps/delay-"${minDelay}"-"${delays[${minDelay}]}"-cm.yaml -n saevecke
  for podCount in 16 8 4 2 1
  do
      strategyName="fixed_p${podCount}_d${minDelay}%${delays[${minDelay}]}"
      deploymentFile=./resources/deployment/graalvm/setups/fixed/deployment-"${podCount}".yaml
      echo "strategy=${strategyName} - pods=${podCount}"
      echo "deployment file: ${deploymentFile}"
      echo "delay: ${minDelay} <-> ${delays[${minDelay}]}"
      source run_experiment.sh ${iterations} ${podCount} "${strategyName}" ${mealyType} ${statisticsFileName} true ${deploymentFile}
  done
done

source run_experiment.sh ${iterations} 1 "fixed_p1_jvm" ${mealyType} ${statisticsFileName} true ./resources/deployment/jvm/setups/fixed/deployment-1.yaml
