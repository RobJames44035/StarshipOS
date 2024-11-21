#!/bin/bash
rm -rfv ./busybox/build
rm -rfv ./starship_kernel/build
rm -rfv ./initramfs/build
rm -rfv ./grub/build
rm -rfv ./java/build
rm -rfv ./live_cd/build
rm -rfv ./system-bridge-cpp/build
rm -rfv .system-bridge-java/build
rm -rfv ./userland-java/build
mvn clean
