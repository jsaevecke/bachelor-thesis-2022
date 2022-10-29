#!/bin/zsh
echo "Elapsed - Replicas - Running - Unavailable" >> "$1"
start_time=$SECONDS
while sleep 1;
do
  elapsed=$(( SECONDS - start_time ))
  replica_count=$(microk8s kubectl -n saevecke describe deploy sul | grep desired | awk '{print $2}' | head -n1)
  running_count=$(microk8s kubectl get pods -n saevecke --no-headers -l=app=sul | grep -c Running)
  unavailable_count=$(microk8s kubectl get pods -n saevecke --no-headers -l=app=sul | grep -c -v Running)
  echo "$elapsed - $replica_count - $running_count - $unavailable_count" >> "$1"
done


