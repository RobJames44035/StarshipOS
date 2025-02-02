#!/bin/sh
./mvnw clean install
qemu-system-x86_64 -m 4096 -smp 2 -kernel buildroot/buildroot/output/images/bzImage \
  -drive file=buildroot/buildroot/output/images/rootfs.ext4,if=ide,format=raw \
  -netdev user,id=net0,hostfwd=tcp::5005-:5005 -device e1000,netdev=net0 \
  -append "root=/dev/sda rw console=ttyS0 raid=noautodetect init=/init" -serial mon:stdio
