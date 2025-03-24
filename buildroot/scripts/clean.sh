#!/bin/bash

# Path to the rootfs.ext4 file
FILE="./buildroot/output/images/rootfs.ext4"

function clean() {
  cp -v scripts/default.config buildroot/.config

  if [ ! -f "$FILE" ]; then
    cd buildroot || exit
    make clean
  else
    echo "'$FILE' exists. Skipping clean."
  fi
}

function main() {
figlet "buildroot clean"
  clean
}