#!/bin/sh

device_names=$(adb devices | grep 'device$' | awk '{print $1}')

echo "found devices:"
echo "${device_names}"

for device in ${device_names}
do

  if [ "$1" == "phone" ]
  then
    adb -s "$device" install -r "$2"
    adb -s "$device" shell am start "$3"
  fi

done
