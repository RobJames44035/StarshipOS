#!/bin/bash

BUILD_DIR=$1
INITRAMFS_BUILD_DIR=$2
ISO_ROOT_DIR=$BUILD_DIR/iso_root
ISO_OUTPUT_DIR=$BUILD_DIR
ISO_NAME="StarshipOS.iso"

echo "\$1" "$1"
echo "\$2" "$2"
pause

# Create necessary directories
mkdir -p $ISO_ROOT_DIR/boot/grub

# Copy kernel into iso_root/boot
cp -v $BUILD_DIR/starship $ISO_ROOT_DIR/boot

# Copy initramfs image into iso_root/boot
cp -v $INITRAMFS_BUILD_DIR/initramfs $ISO_ROOT_DIR/boot

# Create the ISO image
grub-mkrescue -o $ISO_OUTPUT_DIR/$ISO_NAME $ISO_ROOT_DIR

echo "Bootable ISO created successfully: $ISO_OUTPUT_DIR/$ISO_NAME"
