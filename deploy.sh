#!/bin/bash
iterations=${1}
# shellcheck disable=SC2034
for iteration in $(seq "${iterations}"); do
    microk8s kubectl delete deployment coffee-sul -n saevecke
    microk8s kubectl delete scaledobject rabbitmq-scaledobject -n saevecke

    for index in "${@:4}"
    do
        microk8s kubectl apply -f "$index"
        sleep "${3}"
    done

    source ./monitor-replicas.sh "${iteration}_${2}_replicas_log.txt" &
    RUNNING_PID=$!
    java -DSAVE_RESULTS_TO_FILE=true -DFILENAME_PREFIX="${iteration}_${2}" -jar ./learner.jar --spring.rabbitmq.port=30640
    kill ${RUNNING_PID}
done