#!/bin/bash

build_id=${1}
git_repo=https://github.com/vicozizou/semefa-gateway.git
src_folder=semefa-gateway

build_by_commit() {

mkdir -p /build/src && cd /build/src
echo "Pulling semefa-gateway src" \
  && git clone ${git_repo} ${src_folder} \
  && git gc --aggressive \
  && cd  ${src_folder}
  && ./gradlew  --no-daemon downloadDependencies clonePullTomcatDist -PuseGradleNode=false
  cd /build/src/core
  git fetch --all --tags
  git clean -f -d
  git pull

  echo "Checking out commit/tag/branch: $1"
  if [[ ${is_release} == true ]]; then
    echo "Executing: git checkout tags/${1} -b ${1}"
    git checkout tags/${1} -b ${1}
  elif [[ "${1}" != 'master' ]]; then
    echo "Executing: git checkout ${1}"
    git checkout ${1}
  fi

  cd dotCMS && ./gradlew clonePullTomcatDist createDistPrep -PuseGradleNode=false
  find ../dist/  -name "*.sh" -exec chmod 500 {} \;
  mv ../dist/* "${build_target_dir}"
}

function cloneRepo {
  git clone https://github.com/vicozizou/semefa-gateway.git
}