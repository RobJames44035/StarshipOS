#!/bin/bash
#
# /*
#  *
#  * StarshipOS $file.filename Copyright (c) 2025 R. A. James
#  * UPDATED: 2/25/25, 1:18 PM by rajames
#  *
#  * StarshipOS is licensed under GPL2, GPL3, Apache 2
#  *
#  */
#
#

function mount_rootfs() {
  echo "Locating free loop device."
  sudo mkdir -p "/mnt/rootfs"
  LOOPDEV=$(sudo losetup -fP --show "../../buildroot/buildroot/output/images/rootfs.ext4")
  sudo mount -o loop "../../buildroot/buildroot/output/images/rootfs.ext4" "/mnt/rootfs"
  echo "$LOOPDEV @ /mnt/rootfs"
}

function make_dirs() {
  echo "Creating directories."
  sudo mkdir -pv "/mnt/rootfs/var/lib/starship/"
}

function copy_files() {
  echo "Copy files."
  sudo cp -v "target/osgi-manager-1.0.0.jar" "/mnt/rootfs/var/lib/starship/osgi-manager.jar"
}

function unmount_rootfs() {
  echo "Cleanup"
  sudo sync
  sudo umount "/mnt/rootfs/"
}

function main() {
  mount_rootfs
  make_dirs
  copy_files
  unmount_rootfs
}
main
