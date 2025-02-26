#!/bin/bash
#
# StarshipOS Copyright (c) 2025. R.A.James
#
# Licensed under GPL2, GPL3 and Apache 2
#

function mount_rootfs() {
  LOOPDEV=$(sudo losetup -fP --show "../../buildroot/buildroot/output/images/rootfs.ext4")
  sudo mount -o loop "../../buildroot/buildroot/output/images/rootfs.ext4" "/mnt/rootfs"
}

function copy_files() {
  if [[ $INIT_SYSTEM == "true" ]]; then
    echo "Using BusyBox init."
  else
    echo "Using StarshipOS init."
    sudo cp -v "target/sbin-init" "/mnt/rootfs/sbin/init"
  fi
}

function unmount_rootfs() {
  sudo sync
  sudo umount "/mnt/rootfs/"
}

function main() {
  mount_rootfs
  copy_files
  unmount_rootfs
}

# Main script logic
INIT_SYSTEM=$1 # Get the Maven property value as the first argument
main