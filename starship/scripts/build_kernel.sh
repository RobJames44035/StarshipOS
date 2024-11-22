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
  # shellcheck disable=SC2046
  cp $(pwd)/starship_kernel/arch/i386/boot/bzImage $(pwd)/build/boot/starship
fi
