#!/bin/bash

#sh -c "$(curl -fsSL https://raw.githubusercontent.com/vicozizou/semefa-gateway/master/scripts/seed-docker.sh)"

: ${BRANCH:='master'} && export BRANCH
script_resources=(docker-compose.yml runGw.sh setup.sh)

cd ${HOME}
mkdir -p sac/semefa-gateway
cd sac/semefa-gateway
[[ -d semefa-gateway ]] && rm -rf ./semefa-gateway
git clone https://github.com/vicozizou/semefa-gateway.git
ls -las .

for res in "${script_resources[@]}"
do
  echo "Preparing ${res}..."
  mv ./semefa-gateway/scripts/${res} ./
done

rm -rf ./semefa-gateway
ls -las .