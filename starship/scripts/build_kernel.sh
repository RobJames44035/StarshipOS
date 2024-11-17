#!/bin/bash
# shellcheck disable=SC2164
if [ ! -d build ]; then
  cd ./starship_kernel
  make clean
  make
  cd ../
  mkdir -p ./build
  cp ./starship_kernel/vmlinux ./build/starship
  cd ./starship_kernel
  make clean
  cd ../
  sudo chown root:root ./build/starship
fi
