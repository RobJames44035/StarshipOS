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
fi
