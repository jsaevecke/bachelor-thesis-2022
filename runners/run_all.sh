#!/bin/bash

iterations=${1}
statisticsFileName=${2}
mealyType=coffee

declare -A heavyDelays=([500]=1000 [3000]=5000 [5000]=10000)
declare -A lightDelays=([100]=150)

iterations=${1}
# shellcheck disable=SC2034
for iteration in $(seq "${iterations}"); do
  microk8s kubectl apply -f ./resources/deployment/configmaps/general-cm.yaml -n saevecke
  microk8s kubectl apply -f ./resources/deployment/configmaps/dispose-cm.yaml -n saevecke
  microk8s kubectl apply -f ./resources/deployment/configmaps/coffee-cm.yaml -n saevecke
  microk8s kubectl apply -f ./resources/deployment/configmaps/no-file-cm.yaml -n saevecke

  microk8s kubectl apply -f ./resources/deployment/configmaps/delay-0-0-cm.yaml -n saevecke
  for podCount in 32 64
  do
     strategyName="fixed_p${podCount}_d0%0"
     deploymentFile=./resources/deployment/graalvm/setups/fixed/deployment-"${podCount}".yaml
      echo "strategy=${strategyName} - pods=${podCount}"
      echo "deployment file: ${deploymentFile}"
      echo "delay: 0 <-> 0"
      source run_experiment.sh 1 ${podCount} "${strategyName}" ${mealyType} ${statisticsFileName} true ${deploymentFile}
  done

  for minDelay in "${!lightDelays[@]}"
  do
    microk8s kubectl apply -f ./resources/deployment/configmaps/delay-"${minDelay}"-"${lightDelays[${minDelay}]}"-cm.yaml -n saevecke
    for podCount in 32
    do
        strategyName="fixed_p${podCount}_d${minDelay}%${lightDelays[${minDelay}]}"
        deploymentFile=./resources/deployment/graalvm/setups/fixed/deployment-"${podCount}".yaml
        echo "strategy=${strategyName} - pods=${podCount}"
        echo "deployment file: ${deploymentFile}"
        echo "delay: ${minDelay} <-> ${lightDelays[${minDelay}]}"
        source run_experiment.sh 1 ${podCount} "${strategyName}" ${mealyType} ${statisticsFileName} true ${deploymentFile}
    done
  done

  for minDelay in "${!heavyDelays[@]}"
  do
    microk8s kubectl apply -f ./resources/deployment/configmaps/delay-"${minDelay}"-"${heavyDelays[${minDelay}]}"-cm.yaml -n saevecke
    for podCount in 1 2 4 16 64
    do
        strategyName="fixed_p${podCount}_d${minDelay}%${heavyDelays[${minDelay}]}"
        deploymentFile=./resources/deployment/graalvm/setups/fixed/deployment-"${podCount}"-heavy.yaml
        echo "strategy=${strategyName} - pods=${podCount}"
        echo "deployment file: ${deploymentFile}"
        echo "delay: ${minDelay} <-> ${heavyDelays[${minDelay}]}"
        source run_experiment.sh 1 ${podCount} "${strategyName}" ${mealyType} ${statisticsFileName} true ${deploymentFile}
    done
  done

  microk8s kubectl apply -f ./resources/deployment/configmaps/delay-0-0-cm.yaml -n saevecke
  for hpa in 5 15 30 530
  do
    for podCount in 0 1
    do
        strategyName="keda_p${podCount}_hpa${hpa}_d0%0"
        deploymentFile=./resources/deployment/graalvm/setups/keda/deployment-"${podCount}".yaml
        kedaFile=./resources/deployment/graalvm/setups/keda/queue-length/min"${podCount}"_hpa"${hpa}".yaml
        echo "strategy=${strategyName} - pods=${podCount}"
        echo "deployment file: ${deploymentFile}"
        echo "keda file: ${kedaFile}"
        echo "delay: 0 <-> 0"
        source run_experiment.sh 1 ${podCount} "${strategyName}" ${mealyType} ${statisticsFileName} true ${deploymentFile} ${kedaFile}
    done
  done

  for minDelay in "${!lightDelays[@]}"
  do
    microk8s kubectl apply -f ./resources/deployment/configmaps/delay-"${minDelay}"-"${lightDelays[${minDelay}]}"-cm.yaml -n saevecke
    for hpa in 5 15 30 530
    do
      for podCount in 0 1
      do
          strategyName="keda_p${podCount}_hpa${hpa}_d${minDelay}%${lightDelays[${minDelay}]}"
          deploymentFile=./resources/deployment/graalvm/setups/keda/deployment-"${podCount}".yaml
          kedaFile=./resources/deployment/graalvm/setups/keda/queue-length/min"${podCount}"_hpa"${hpa}".yaml
          echo "strategy=${strategyName} - pods=${podCount}"
          echo "deployment file: ${deploymentFile}"
          echo "keda file: ${kedaFile}"
         echo "delay: ${minDelay} <-> ${lightDelays[${minDelay}]}"
          source run_experiment.sh 1 ${podCount} "${strategyName}" ${mealyType} ${statisticsFileName} true ${deploymentFile} ${kedaFile}
      done
    done
  done

  for minDelay in "${!heavyDelays[@]}"
    do
      microk8s kubectl apply -f ./resources/deployment/configmaps/delay-"${minDelay}"-"${heavyDelays[${minDelay}]}"-cm.yaml -n saevecke
      for hpa in 5 15 30 530
      do
        for podCount in 0 1
        do
            strategyName="keda_p${podCount}_hpa${hpa}_d${minDelay}%${heavyDelays[${minDelay}]}"
            deploymentFile=./resources/deployment/graalvm/setups/keda/deployment-"${podCount}"-heavy.yaml
            kedaFile=./resources/deployment/graalvm/setups/keda/queue-length/min"${podCount}"_hpa"${hpa}".yaml
            echo "strategy=${strategyName} - pods=${podCount}"
            echo "deployment file: ${deploymentFile}"
            echo "keda file: ${kedaFile}"
            echo "delay: ${minDelay} <-> ${heavyDelays[${minDelay}]}"
            source run_experiment.sh 1 ${podCount} "${strategyName}" ${mealyType} ${statisticsFileName} true ${deploymentFile} ${kedaFile}
        done
      done
    done
done

microk8s kubectl apply -f ./resources/deployment/configmaps/delay-0-0-cm.yaml -n saevecke
source run_experiment.sh 3 1 "fixed_p1_jvm_d0%0" ${mealyType} ${statisticsFileName} true ./resources/deployment/jvm/setups/fixed/deployment-1.yaml
