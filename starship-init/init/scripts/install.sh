#!/bin/bash
#
# StarshipOS Copyright (c) 2025. R.A. James
#
figlet "Init install."


function mount() {
  LOOPDEV=$(sudo losetup -fP --show ../../buildroot/buildroot/output/images/rootfs.ext4)
  sudo mount -o loop ../../buildroot/buildroot/output/images/rootfs.ext4 /mnt/rootfs
}

function make_dirs() {
  sudo mkdir -p /mnt/rootfs/var/lib/starship/
  sudo mkdir -p /mnt/rootfs/etc/starship/config.d
}

function copy_files() {
  sudo cp -v target/init-0.1.0-SNAPSHOT.jar /mnt/rootfs/var/lib/starship/init.jar
  sudo cp -v src/main/resources/default-init.groovy /mnt/rootfs/etc/starship/config.d/default.groovy
  sudo cp -v src/main/resources/init.sh /mnt/rootfs/init
  sudo chmod +x /mnt/rootfs/init
}

function unmount() {
  sudo sync
  sudo umount /mnt/rootfs/
}

function main() {
  mount
  make_dirs
  copy_files
  unmount
}
main
