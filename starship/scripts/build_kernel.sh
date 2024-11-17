#!/bin/bash
# shellcheck disable=SC2164
if [ ! -d build ]; then
  cd ./starship_kernel
  make clean
  make tinyconf
  make
  mkdir -p ../build
  echo $PWD
  # shellcheck disable=SC2162
  read -p "Press a key..."
  cp ./arch/i386/boot/bzimage ../build/starship
#  make clean
#  sudo chown root:root ../build/starship
#  sudo chmod 4755 ../build/starship
  cd ../
fi
