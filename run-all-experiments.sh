#!/bin/bash
source run_experiment.sh 3 "${1}_fixed_1" 10 false ./resources/deployment/graalvm/setups/fixed/deployment-1.yaml
source run_experiment.sh 3 "${1}_fixed_2" 10 false ./resources/deployment/graalvm/setups/fixed/deployment-2.yaml
source run_experiment.sh 3 "${1}_fixed_4" 15 false ./resources/deployment/graalvm/setups/fixed/deployment-4.yaml
source run_experiment.sh 3 "${1}_fixed_6" 15 false ./resources/deployment/graalvm/setups/fixed/deployment-6.yaml
source run_experiment.sh 3 "${1}_fixed_8" 20 false ./resources/deployment/graalvm/setups/fixed/deployment-8.yaml
source run_experiment.sh 3 "${1}_fixed_10" 20 false ./resources/deployment/graalvm/setups/fixed/deployment-10.yaml
source run_experiment.sh 3 "${1}_fixed_12" 25 false ./resources/deployment/graalvm/setups/fixed/deployment-12.yaml
source run_experiment.sh 3 "${1}_fixed_14" 25 false ./resources/deployment/graalvm/setups/fixed/deployment-14.yaml
source run_experiment.sh 3 "${1}_fixed_16" 30 false ./resources/deployment/graalvm/setups/fixed/deployment-16.yaml

source run_experiment.sh 3 "${1}_ql1_min0" 10 false ./resources/deployment/graalvm/setups/keda/deployment-0.yaml ./resources/deployment/graalvm/setups/keda/queue-length/ql1_min0.yaml
source run_experiment.sh 3 "${1}_ql1_min1" 10 false ./resources/deployment/graalvm/setups/keda/deployment-0.yaml ./resources/deployment/graalvm/setups/keda/queue-length/ql1_min1.yaml
source run_experiment.sh 3 "${1}_ql1_min2" 15 false ./resources/deployment/graalvm/setups/keda/deployment-0.yaml ./resources/deployment/graalvm/setups/keda/queue-length/ql1_min2.yaml
source run_experiment.sh 3 "${1}_ql1_min3" 15 false ./resources/deployment/graalvm/setups/keda/deployment-0.yaml ./resources/deployment/graalvm/setups/keda/queue-length/ql1_min3.yaml
source run_experiment.sh 3 "${1}_ql1_min4" 15 false ./resources/deployment/graalvm/setups/keda/deployment-0.yaml ./resources/deployment/graalvm/setups/keda/queue-length/ql1_min4.yaml