#!/bin/bash
#
# StarshipOS Copyright (c) 2025. R.A. James
#

function main() {
  figlet "buildroot install."
  cp -v "./buildroot/output/images/rootfs.ext4" "../"
}

main
