#!/bin/bash

WD=$(pwd)
echo WD = $WD

user=$(whoami)
echo user = $user

project_name=quarkus-vlille-demo
echo project-name = $project_name

if [ "build-image" == $1 ]; then
  
  file=${WD}/src/main/docker/Dockerfile.vlille.jvm 
  
  tag=codespace/${project_name}:1.0-SNAPSHOT  
  
  echo docker build  -f $file  -t $tag $WD
  docker build  -f $file  -t $tag $WD --target run-dev-stack
fi

if [ "update-build-image" == $1 ]; then
  
  file=${WD}/src/main/docker/Dockerfile.vlille.jvm 
  
  tag=jcsim/maven-build:latest  
  
  target=cache-m2

  registry="${tag}"

  echo docker build  -f $file  -t $tag $WD --target $target
  docker build  -f $file  -t $tag $WD --target $target
  cat ~/my_password.txt | docker login --username jcsim --password-stdin
  echo docker image push $registry
  docker image push $registry
fi

if [ "env-test" == $1 ]; then
  file=${WD}/src/test/docker/docker-compose.stack.test.yml
  upDown=$2
  docker-compose -f $file $upDown
fi
 
if [ "env-dev" == $1 ]; then
  file=${WD}/src/main/docker/docker-compose.stack.mongo.yml
  upDown=$2
  
  echo docker-compose -f $file $upDown
  docker-compose -f $file $upDown
fi

if [ "env-run" == $1 ]; then
  file=${WD}/src/main/docker/docker-compose.stack.mongo.yml
  upDown=$2
  
  file2=${WD}/src/main/docker/docker-compose.stack.quarkus.yml
  
  echo docker-compose -f $file -f $file2 $upDown
  docker-compose -f $file -f $file2 $upDown
fi


if [ "env-test-run" == $1 ]; then
  file=${WD}/src/test/docker/docker-compose.test-run.yml
  upDown=$2
  
  echo docker-compose -f $file $upDown --build
  docker-compose -f $file $upDown
fi 
  
if [ "jdeps" == $1 ]; then 
#jdeps --ignore-missing-deps  -q --multi-release 17 --print-module-deps --class-path target/    target/quarkus-app/app/getting-started-1.0-SNAPSHOT.jar
#> jre-deps.info
# pipe the result of running jdeps on the app jar to file
jdeps --ignore-missing-deps  --multi-release 17 -q  --print-module-deps target/getting-started-1.0-SNAPSHOT-runner.jar > jre-deps.info

fi

if [ "jlink" == $1 ]; then

  jlink --verbose \
    --compress 2 \
    --strip-java-debug-attributes \
    --no-header-files \
    --no-man-pages \
    --output jre \
    --add-modules $(cat jre-deps.info)
fi
