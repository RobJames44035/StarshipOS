#!/bin/bash
#
# StarshipOS Copyright (c) 2025. R.A.James
#
# Licensed under GPL2, GPL3 and Apache 2
#
#source "../../scripts/fs_library.sh"
#function mount_rootfs() {
#  # shellcheck disable=SC2034
#  LOOPDEV=$(sudo losetup -fP --show "../../buildroot/buildroot/output/images/rootfs.ext4")
#  sudo mount -o loop "../../buildroot/buildroot/output/images/rootfs.ext4" "/mnt/rootfs"
#}
#
#function copy_files() {
#    sudo cp -v "target/sbin-init" "/mnt/rootfs/sbin/init"
#}
#
#function main() {
#  if [[ $1 = 'true' ]]; then
#    echo "**************************"
#    echo "*  Using BusyBox init.   *"
#    echo "**************************"
#  else
#    echo "**************************"
#    echo "* Using StarshipOS init. *"
#    echo "**************************"
#    copy_files
#  fi
#}
#main $1
