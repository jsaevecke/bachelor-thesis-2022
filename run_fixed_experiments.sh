#!/bin/bash
source run_experiment.sh "${2}" "${1}_fixed_1_" 10 false ./resources/deployment/graalvm/setups/fixed/deployment-1.yaml
source run_experiment.sh "${2}" "${1}_fixed_2_" 10 false ./resources/deployment/graalvm/setups/fixed/deployment-2.yaml
source run_experiment.sh "${2}" "${1}_fixed_4_" 15 false ./resources/deployment/graalvm/setups/fixed/deployment-4.yaml
source run_experiment.sh "${2}" "${1}_fixed_6_" 15 false ./resources/deployment/graalvm/setups/fixed/deployment-6.yaml
source run_experiment.sh "${2}" "${1}_fixed_8_" 20 false ./resources/deployment/graalvm/setups/fixed/deployment-8.yaml
source run_experiment.sh "${2}" "${1}_fixed_10_" 20 false ./resources/deployment/graalvm/setups/fixed/deployment-10.yaml
source run_experiment.sh "${2}" "${1}_fixed_12_" 25 false ./resources/deployment/graalvm/setups/fixed/deployment-12.yaml
source run_experiment.sh "${2}" "${1}_fixed_14_" 25 false ./resources/deployment/graalvm/setups/fixed/deployment-14.yaml
source run_experiment.sh "${2}" "${1}_fixed_16_" 30 false ./resources/deployment/graalvm/setups/fixed/deployment-16.yaml
