#!/bin/sh

curr_dir=$(pwd)

isClean=false
isBuild=false
isOffline=false

cd ..

while [ $# != 0 ]
do
  if [ "$1" == "-b" ]; then
    isBuild=true
  elif [ "$1" == "-c" ]; then
    isClean=true
  elif [ "$1" == "-o" ]; then
    isOffline=true
  fi
  shift
done

if ${isOffline}; then
  params=${params}" --offline"
fi

if ${isClean}; then
  echo isClean
  ./gradlew clean
fi


if ${isBuild}; then
  echo "${params}"
  ./gradlew assembleDebug ${params}

  cd "${curr_dir}"

  bash install.sh phone ../app/build/outputs/apk/debug/app-debug.apk com.cxy.oi/.app.ui.LauncherUI
fi

