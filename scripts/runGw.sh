#!/bin/bash

profile=$1
[[ -n "${profile}" ]] && profile='dev'
env_file=".env.${profile}"

env $(cat ${env_file} | xargs) docker-compose -f docker-compose.yml
