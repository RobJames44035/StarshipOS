#!/bin/bash
#
# StarshipOS Copyright (c) 2025. R.A. James
#
figlet "Installing..."
LOOPDEV=$(sudo losetup -fP --show ../../buildroot/buildroot/output/images/rootfs.ext4)
sudo mount -o loop buildroot/buildroot/output/images/rootfs.ext2 /mnt/rootfs
sudo cp -v init-0.1.0-SNAPSHOT.jar /mnt/rootfs/var/lib/starship/init.jar
sudo sync
sudo umount /mnt/rootfs/
sudo lsof +f -- "$LOOPDEV"
