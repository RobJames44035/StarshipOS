#!/bin/bash

# Path to the rootfs.ext4 file
FILE="buildroot/output/images/rootfs.ext4"
cp -pv scripts/default.config buildroot/.config

# Check if DISTCLEAN is set to true, and trigger make distclean if necessary
if [ "$DISTCLEAN" = "true" ]; then
  cd buildroot || exit
  make distclean
  exit 0
fi

if [ ! -f "$FILE" ]; then
  cd buildroot || exit
  make clean
else
  echo "'$FILE' exists. Skipping clean."
fi
