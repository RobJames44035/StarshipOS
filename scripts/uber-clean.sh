#!/bin/bash
clear

rm -rfv ./starship/build
rm -rfv ./busybox/build
rm -rfv ./java/build

rm -rfv ./system-bridge-cpp/build
rm -rfv .system-bridge-java/build
rm -rfv ./userland-java/build

rm -rfv ./initramfs/build
rm -rfv ./grub/build
rm -rfv ./live_cd/build

mvn clean
