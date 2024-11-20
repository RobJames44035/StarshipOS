#!/bin/bash
# shellcheck disable=SC2162

BUILD_DIR=$1
INITRAMFS_BUILD_DIR=$2
ISO_ROOT_DIR=$BUILD_DIR/iso_root
ISO_OUTPUT_DIR=$BUILD_DIR
ISO_NAME="StarshipOS.iso"

echo "\$1" "$1"
echo "\$2" "$2"
read -p "Pause..."

# Create necessary directori
mkdir -p "$ISO_ROOT_DIR"/boot/grub
read -p "Pause..."

# Copy kernel into iso_root/boot
cp -v "$BUILD_DIR"/starship "$ISO_ROOT_DIR"/boot
read -p "Pause..."

# Copy initramfs image into iso_root/boot
cp -v "$INITRAMFS_BUILD_DIR"/initramfs "$ISO_ROOT_DIR"/boot
read -p "Pause..."

# Create the ISO image
grub-mkrescue -o "$ISO_OUTPUT_DIR"/$ISO_NAME "$ISO_ROOT_DIR"

echo "Bootable ISO created successfully: $ISO_OUTPUT_DIR/$ISO_NAME"
