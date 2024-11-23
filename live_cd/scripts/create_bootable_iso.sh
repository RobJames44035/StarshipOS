#!/bin/bash
# shellcheck disable=SC2162

set -e  # Exit immediately if a command exits with a non-zero status
set -u  # Treat unset variables as an error

ISO_NAME="StarshipOS.iso"
CURRENT_DIR="${PWD}"
BUILD_DIR="${CURRENT_DIR}/build"
ISO_IMAGE_DIR="${CURRENT_DIR}/build/iso-image"
GRUB_DIR="${ISO_IMAGE_DIR}/boot/grub"

KERNEL_SOURCE_PATH="${CURRENT_DIR}/../starship/build/boot/starship"
INITRAMFS_SOURCE_PATH="${CURRENT_DIR}/../initramfs/build/initramfs.img"
GRUB_CFG_SOURCE_PATH="${CURRENT_DIR}/../grub/build/boot/grub/grub.cfg"

# Logging function
function log() {
    echo "[$(date +'%Y-%m-%d %H:%M:%S')] $1"
}

log "S tarting ISO creation script."+++
if [ ! -d "$BUILD_DIR" ]; then
    # Create necessary directories
    log "Creating necessary directories..."
    mkdir -p "$GRUB_DIR"

    # Copy kernel into iso_root/boot
    log "Copying kernel to ISO image..."
    cp -v "$KERNEL_SOURCE_PATH" "${ISO_IMAGE_DIR}/boot/starship"

    # Copy initramfs image into iso_root/boot
    log "Copying initramfs image to ISO image..."
    cp -v "$INITRAMFS_SOURCE_PATH" "${ISO_IMAGE_DIR}/boot/initramfs.img"

    # Copy grub.cfg into iso_root/boot/grub
    log "Copying grub.cfg to ISO image..."
    cp -v /home/rajames/PROJECTS/StarshipOS/grub/build/boot/grub.cfg "${ISO_IMAGE_DIR}/boot/grub/grub.cfg"
    # Create the ISO image
    log "Creating the ISO image..."
    mkdir -p "$BUILD_DIR"
    sudo grub-mkrescue -o "${BUILD_DIR}/${ISO_NAME}" "$ISO_IMAGE_DIR"

    log "Bootable ISO created successfully: ${BUILD_DIR}/${ISO_NAME}"
else
    log "Nothing to do."
fi

