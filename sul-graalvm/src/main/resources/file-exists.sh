#!/bin/bash

FILE=./finished
while true
do
   if test -f "$FILE"; then
     echo "$FILE exists."
     exit 0
   else
     echo "file does not exist"
     sleep 1s
   fi
done

