#!/bin/bash
isDeployed() {
  local running_count=0
  running_count=$(microk8s kubectl get pods -n saevecke --no-headers -l=app=sul | grep -c Running)
  if [ "$1" -eq "$running_count" ]; then
    return 0
  fi
  return 1
}

microk8s kubectl port-forward --namespace saevecke svc/mu-rabbit-rabbitmq 15672:15672 2>/dev/null &
sleep 5

./rabbitmqadmin delete queue name=sul_input_q
./rabbitmqadmin delete queue name=sul_output_q
sleep 5

iterations=${1}
# shellcheck disable=SC2034
for iteration in $(seq "${iterations}"); do
    microk8s kubectl delete deployment sul -n saevecke 2>/dev/null
    microk8s kubectl delete scaledobject rabbitmq-scaledobject -n saevecke 2>/dev/null
    microk8s kubectl delete hpa keda-hpa-rabbitmq-scaledobject -n saevecke 2>/dev/null

    for index in "${@:7}"
    do
        microk8s kubectl apply -f "$index"
        sleep 5
    done

    desiredPodCount=${2}
    until isDeployed ${desiredPodCount}; do
      echo "$(microk8s kubectl get pods -n saevecke --no-headers -l=app=sul | grep -c Running) of ${desiredPodCount} are running..."
      sleep 5
    done

    source ./runners/monitor-replicas.sh "${iteration}_${3}_monitoring.txt" &
    RUNNING_PID=$!
    java -DSAVE_RESULTS_TO_FILE=true -DSAVE_COMPLETED_HYPOTHESIS_MODEL=false -DMEALY_MACHINE_TYPE="${4}" -DSTRATEGY_NAME="${3}" -DFILENAME_PREFIX="${5}" -jar ./learner.jar --spring.rabbitmq.port=30640
    kill ${RUNNING_PID}
done
microk8s kubectl delete deployment sul -n saevecke