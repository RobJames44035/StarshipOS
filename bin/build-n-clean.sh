#!/bin/bash
clear

#sudo rm -rfv ./starship/build
#sudo rm -rfv ./java/build
#sudo rm -rfv ./busybox/build

sudo rm -rfv ./system-bridge/build
sudo rm -rfv ./userland-java/build
sudo rm -rfv ./starship-sdk/build

sudo rm -rfv ./initramfs/build
sudo rm -rfv ./grub/build
sudo rm -rfv ./live_cd/build

sudo ./mvnw clean
./mvnw install

