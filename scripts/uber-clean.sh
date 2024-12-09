#!/bin/bash
clear

sudo rm -rfv ./starship/build
#rm -rfv ./java/build
#rm -rfv ./busybox/build

rm -rfv ./system-bridge/build
rm -rfv ./userland-java/build
rm -rfv ./starship-sdk/build

rm -rfv ./initramfs/build
rm -rfv ./grub/build
rm -rfv ./live_cd/build

sudo mvn clean
