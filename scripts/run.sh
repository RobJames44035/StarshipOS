#!/bin/sh
#
# StarshipOS Copyright (c) 2025. R. A. James
#

clear
figlet "Building & Running"
figlet "StarshipOS QEMU"
./mvnw clean install
qemu-system-x86_64 -m 4096 -smp 2 -kernel buildroot/buildroot/output/images/bzImage \
  -drive file=buildroot/buildroot/output/images/rootfs.ext4,if=ide,format=raw \
  -netdev user,id=net0,hostfwd=tcp::5005-:5005 -device e1000,netdev=net0 \
  -append "root=/dev/sda rw console=ttyS0 init=/sbin/init" -serial mon:stdio
