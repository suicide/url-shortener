#!/bin/bash

for i in {1..100000}
do
  string=$(cat /dev/urandom | tr -dc 'a-zA-Z0-9' | fold -w 10 | head -n 1)
  curl -X POST -d "$string" -H "Content-Type: text/plain" http://localhost:8080/api/v1
done



