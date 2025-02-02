#!/bin/bash
#
# StarshipOS Copyright (c) 2025. R.A. James
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

function dbus() {
  echo "dbus-uuidgen --ensure=/mnt/rootfs/var/lib/dbus/machine-id"
  dbus-uuidgen --ensure=/mnt/rootfs/var/lib/dbus/machine-id
  sudo  chmod 644 "/mnt/rootfs/var/lib/dbus/machine-id"
  sudo cp -pv "/mnt/rootfs/var/lib/dbus/machine-id" "/etc/machine-id"
}

function main() {
  mount_rootfs
  make_dirs
  dbus
  copy_files
  unmount_rootfs
}
main
