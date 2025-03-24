#!/bin/bash
#
# StarshipOS Copyright (c) 2025. R.A. James
#
function compile() {
  cp -v "./scripts/default.config" "buildroot/.config"

  cd "./buildroot" || exit
  make oldconfig
  make all
  cd "../"
}

function main() {
  figlet "buildroot compile"
  compile
}

main