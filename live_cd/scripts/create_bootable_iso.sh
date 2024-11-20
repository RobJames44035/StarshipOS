#!/bin/bash
# shellcheck disable=SC2162
ISO_NAME="StarshipOS.iso"


# Create necessary directories
mkdir -p /home/rajames/PROJECTS/StarshipOS/live_cd/build/boot/grub

# Copy kernel into iso_root/boot
cp -v /home/rajames/PROJECTS/StarshipOS/starship/build/boot/starship /home/rajames/PROJECTS/StarshipOS/live_cd/build/boot

# Copy initramfs image into iso_root/boot
cp -v /home/rajames/PROJECTS/StarshipOS/initramfs/build/initramfs_img.gz /home/rajames/PROJECTS/StarshipOS/live_cd/build/boot

# Copy grub.cfg into iso_root/boot/grub.cfg
cp -v /home/rajames/PROJECTS/StarshipOS/grub/build/boot/grub/grub.cfg /home/rajames/PROJECTS/StarshipOS/live_cd/build/boot/grub
# Create the ISO image
grub-mkrescue -o "$ISO_OUTPUT_DIR"/$ISO_NAME /home/rajames/PROJECTS/StarshipOS/live_cd/build

echo "Bootable ISO created successfully: $ISO_OUTPUT_DIR/$ISO_NAME"
