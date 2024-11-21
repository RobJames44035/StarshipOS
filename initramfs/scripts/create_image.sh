#!/bin/bash
# shellcheck disable=SC2164

if [ ! -d build ]; then
  mkdir -p target/initramfs_img
  cp -r /home/rajames/PROJECTS/StarshipOS/busybox/build/* target/initramfs_img
  #++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  #             Copy any other content for the init ramdisk here
  #++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

# Ensure /dev/null and /dev/console exist
  mkdir -p target/initramfs_img/dev
  sudo mknod -m 666 target/initramfs_img/dev/null c 1 3
  sudo mknod -m 600 target/initramfs_img/dev/console c 5 1

# Create the initramfs image
  output_image="target/initramfs.img"
  (cd target/initramfs_img && find . -print0 | cpio --null --create --verbose --format=newc | gzip --best) > $output_image
  mkdir -p build
  cp target/initramfs.img build
  echo "initramfs.img has been created successfully."
fi
