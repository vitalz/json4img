#!/usr/bin/env bash

for s in {1..60}
do
  for i in {1..25}
  do
    sleep 0.04
    echo "$(date): ( second = $s, frame = $i )"
    curl -s -X GET "http://127.0.0.1:8080/json4img/image/display/test"
  done
done