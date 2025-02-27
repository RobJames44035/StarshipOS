#!/bin/bash
#
# StarshipOS Copyright (c) 2025. R.A.James
#
# Licensed under GPL2, GPL3 and Apache 2
#

# Source the centralized library
source "../../scripts/fs_library.sh"

function make_dirs() {
  echo "Creating directories."
  sudo mkdir -pv "/mnt/rootfs/var/lib/starship/"
}

function copy_files() {
  echo "Copy files."
  sudo cp -v "target/osgi-manager-1.0.0.jar" "/mnt/rootfs/var/lib/starship/osgi-manager.jar"
}

function main() {
  mount_rootfs "../../buildroot/buildroot/output/images/rootfs.ext4"
  make_dirs
  copy_files
  unmount_rootfs
}
main
