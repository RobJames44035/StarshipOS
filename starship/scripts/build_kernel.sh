#!/bin/bash
# shellcheck disable=SC2164
# shellcheck disable=SC2162
if [ ! -d build ]; then
  cd ./starship_kernel
  make clean
  make x86_64_defconfig
  make
  cd ../
  mkdir -p build/boot
  cp starship_kernel/arch/x86_64/boot/bzImage build/boot/starship
  make clean
fi
