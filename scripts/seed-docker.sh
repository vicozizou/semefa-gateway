#!/bin/bash

#sh -c "$(curl -fsSL https://raw.githubusercontent.com/vicozizou/semefa-gateway/master/scripts/seed-docker.sh)"

: ${BRANCH:='master'} && export BRANCH
script_resources=(docker-compose.yml runGw.sh setup.sh)

cd ${HOME}
mkdir -p sac/semefa-gateway
cd sac/semefa-gateway

for res in "${script_resources[@]}"
do
  echo "Getting https://raw.githubusercontent.com/vicozizou/semefa-gateway/${BRANCH}/scripts/${res}"
  curl -fsSL https://raw.githubusercontent.com/vicozizou/semefa-gateway/${BRANCH}/scripts/${res} --output ./${res}
  chmod 755 ./${res}
done

ls -las .