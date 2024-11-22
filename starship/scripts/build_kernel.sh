#!/bin/bash
# shellcheck disable=SC2164
# shellcheck disable=SC2162
if [ ! -d build ]; then
  cd ./starship_kernel
  make clean
  make tinyconfig
  make
  cd ../
  mkdir -p build/boot
  cp /home/rajames/PROJECTS/StarshipOS/starship/build/boot/bzImage /home/rajames/PROJECTS/StarshipOS/starship/build/boot/starship
fi
