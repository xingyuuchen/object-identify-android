#!/bin/sh

# Before you run this script, make sure you have installed ProtoBuffer.

cd ..

project_dir=$(pwd)

if [ ! -d "app/src/main/java/com/cxy/oi/autogen" ]; then
  mkdir -p "app/src/main/java/com/cxy/oi/autogen"
fi

cd app/protos

for file in `ls`
do

  if [ ${file##*.} == "proto" ]; then
    echo "processing" ${file}" ..."

    protoc -I=$(pwd) --java_out=${project_dir}/app/src/main/java \
      $(pwd)/${file}
  fi

done
