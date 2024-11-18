#!/bin/bash
# shellcheck disable=SC2164
# shellcheck disable=SC2162
if [ ! -d build ]; then
  cd starship_kernel
  make clean
  make
  cd ../
  mkdir -p build/boot
cp starship_kernel/arch/x86/boot/bzImage build/boot/starship
  echo "PWD: $PWD"
  read -p "Foo Bar Baz"
#  cd ./starship_kernel
#  make clean
#  cd ../
#  sudo chown root:root ./build/starship
fi
