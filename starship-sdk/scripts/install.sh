#!/bin/bash
#
# StarshipOS Copyright (c) 2025. R.A.James
#
# Licensed under GPL2, GPL3 and Apache 2
#

source "../scripts/fs_library.sh"

function copy_files() {
  sudo mkdir -p "/mnt/rootfs/java/lib"
  sudo cp -v "./target/lib/libstarshipclib.so" "/mnt/rootfs/java/lib"
}

function main() {
  log "Installing libstarshipclib.so"
  mount_rootfs "../buildroot/buildroot/output/images/rootfs.ext4"
  copy_files
  unmount_rootfs
}
main
