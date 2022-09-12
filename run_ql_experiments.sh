#!/bin/bash
source run_experiment.sh "${2}" "${1}_ql1_min0_" 10 true ./resources/deployment/graalvm/setups/keda/deployment-0.yaml ./resources/deployment/graalvm/setups/keda/queue-length/ql1_min0.yaml
source run_experiment.sh "${2}" "${1}_ql1_min1_" 10 true ./resources/deployment/graalvm/setups/keda/deployment-0.yaml ./resources/deployment/graalvm/setups/keda/queue-length/ql1_min1.yaml
source run_experiment.sh "${2}" "${1}_ql1_min2_" 15 true ./resources/deployment/graalvm/setups/keda/deployment-0.yaml ./resources/deployment/graalvm/setups/keda/queue-length/ql1_min2.yaml
source run_experiment.sh "${2}" "${1}_ql1_min3_" 15 true ./resources/deployment/graalvm/setups/keda/deployment-0.yaml ./resources/deployment/graalvm/setups/keda/queue-length/ql1_min3.yaml
source run_experiment.sh "${2}" "${1}_ql1_min4_" 15 true ./resources/deployment/graalvm/setups/keda/deployment-0.yaml ./resources/deployment/graalvm/setups/keda/queue-length/ql1_min4.yaml