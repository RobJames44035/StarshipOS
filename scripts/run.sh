#!/bin/sh
qemu-system-x86_64 -kernel buildroot/buildroot/output/images/bzImage -drive file=buildroot/buildroot/output/images/rootfs.ext4,if=ide,format=raw -append "root=/dev/sda rw console=ttyS0 init=/init" -serial mon:stdio
