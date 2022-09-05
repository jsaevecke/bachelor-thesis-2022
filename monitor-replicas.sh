#!/bin/zsh
start_time=$SECONDS
while sleep 1;
do
  elapsed=$(( SECONDS - start_time ))
  replica_count=$(kubectl get deployment coffee-sul -n saevecke -o=jsonpath='{.status.replicas}')
  echo "$elapsed $replica_count" >> "$1"
done