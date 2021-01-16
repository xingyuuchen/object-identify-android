#!/bin/sh

# Before you run this script, make sure you have installed ProtoBuffer.

cd ..

project_dir=$(pwd)

cd app/protos

for file in `ls`
do

  if [ ${file##*.} == "proto" ]; then
    echo "processing" ${file}" ..."

    protoc -I=$(pwd) --java_out=${project_dir}/app/src/main/java \
      $(pwd)/${file}
  fi

done
