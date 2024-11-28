#!/bin/bash
# shellcheck disable=SC2162

set -e  # Exit immediately if a command exits with a non-zero status
set -u  # Treat unset variables as an error

ISO_NAME="StarshipOS.iso"
HOME_DIR="/home/rajames/PROJECTS/StarshipOS"

# Logging function
function log() {
    echo "[$(date +'%Y-%m-%d %H:%M:%S')] $1"
}

log "Starting ISO creation process..."

## Create necessary directories
#log "Creating necessary directories..."
mkdir -p "${HOME_DIR}/live_cd/build/iso-assembly"
# Copy the live_cd directory to the iso-assembly directory
log "Copying live_cd to iso-assembly..."
mv -v "${HOME_DIR}/starship/build/live_cd" "${HOME_DIR}/live_cd/build/iso-assembly"

# move build directories
mv -v "${HOME_DIR}/busybox/build" "${HOME_DIR}/live_cd/build/iso-assembly"


#grub
#log "Copying GRUB2 grub.cfg ISO image..."
#cp "${HOME_DIR}/grub/build/boot/grub/grub.cfg" "${HOME_DIR}/live_cd/build/iso-assembly/boot/grub/grub.cfg"

# Copy the initramfs image into the ISO image directory
#log "Copying initramfs image to ISO image..."
#cp -v "${HOME_DIR}/initramfs/build/initramfs.img" "${HOME_DIR}/live_cd/build/iso-assembly" || { log "Failed to copy initramfs"; exit 1; }


## Copy BusyBox executables and necessary directories into the ISO image
#log "Copying BusyBox..."
#cp -r "$BUSYBOX_SRC_DIR/bin" "$ISO_IMAGE_DIR" || { log "Failed to copy BusyBox bin"; exit 1; }
#cp -r "$BUSYBOX_SRC_DIR/sbin" "$ISO_IMAGE_DIR" || { log "Failed to copy BusyBox sbin"; exit 1; }
#cp -r "$BUSYBOX_SRC_DIR/usr" "$ISO_IMAGE_DIR" || { log "Failed to copy BusyBox usr"; exit 1; }
#cp "$BUSYBOX_SRC_DIR/linuxrc" "$ISO_IMAGE_DIR/linuxrc" || { log "Failed to copy linuxrc"; exit 1; }
#
## Copy the GRUB configuration file into the ISO image
#log "Copying grub.cfg to ISO image..."
#cp -v "$GRUB_CFG_SOURCE_PATH" "$GRUB_DIR/grub.cfg" || { log "Failed to copy grub.cfg"; exit 1; }
#
## Create the ISO image using GRUB
#log "Creating the bootable ISO image..."
#read -p "Build paused. Press Enter to continue..."
#
#sudo grub-mkrescue -o "${BUILD_DIR}/${ISO_NAME}" "${ISO_IMAGE_DIR}" 2>&1 | tee iso_creation.log || { log "ISO creation failed"; exit 1; }
#
#log "Bootable ISO created successfully: ${BUILD_DIR}/${ISO_NAME}"
#
#log "Finished ISO creation process."
