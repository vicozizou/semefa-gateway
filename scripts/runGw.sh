#!/bin/bash

profile=$1
[[ -n "${profile}" ]] && profile='dev'
env_file=".env.${profile}"

[[ ! -f ${env_file} ]] && echo "No existe archivo .env.<perfil>, abortando"
env $(cat ${env_file} | xargs) docker-compose -f docker-compose.yml
