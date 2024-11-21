#!/bin/bash
# shellcheck disable=SC2162
ISO_NAME="StarshipOS.iso"


# Create necessary directories
mkdir -p /home/rajames/PROJECTS/StarshipOS/live_cd/build/iso-image/boot/grub

# Copy kernel into iso_root/boot
cp -v /home/rajames/PROJECTS/StarshipOS/starship/build/boot/starship /home/rajames/PROJECTS/StarshipOS/live_cd/build/iso-image/boot/starship

# Copy initramfs image into iso_root/boot
cp -v /home/rajames/PROJECTS/StarshipOS/initramfs/build/initramfs.img /home/rajames/PROJECTS/StarshipOS/live_cd/build/iso-image/boot/initramfs.img

# Copy grub.cfg into iso_root/boot/grub.cfg
cp -v /home/rajames/PROJECTS/StarshipOS/grub/build/boot/grub/grub.cfg /home/rajames/PROJECTS/StarshipOS/live_cd/build/iso-image/boot/grub/grub.cfg
# Create the ISO image
mkdir -p build
sudo grub-mkrescue -o "$PWD/build/StarshipOS.iso" /home/rajames/PROJECTS/StarshipOS/live_cd/build/iso-image

echo "Bootable ISO created successfully: $PWD/StarshipOS.iso"
