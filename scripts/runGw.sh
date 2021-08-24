#!/bin/bash

profile=${1}
echo "Specified profile: ${profile}"
[[ -z "${profile}" ]] && profile='dev'
env_file=".env.${profile}"
echo "Discovered env file: ${profile}"

[[ ! -f ${env_file} ]] && echo "No existe archivo .env.<perfil>, abortando"
env $(cat ${env_file} | xargs) docker-compose -f docker-compose.yml up
