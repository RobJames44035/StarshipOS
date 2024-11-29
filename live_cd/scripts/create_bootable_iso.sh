#!/bin/bash
# shellcheck disable=SC2162

set -e  # Exit immediately if a command exits with a non-zero status
set -u  # Treat unset variables as an error

ISO_NAME="StarshipOS.iso"
HOME="/home/rajames/PROJECTS/StarshipOS"

# Logging function
function log() {
    echo "[$(date +'%Y-%m-%d %H:%M:%S')] $1"
}

log "Starting ISO creation process..."

## Create necessary directories
#log "Creating necessary directories..."
mkdir -p "${HOME}/live_cd/build/iso-assembly"
# Copy the live_cd directory to the iso-assembly directory
log "Copying live_cd to iso-assembly..."
cp -rv $HOME/starship/build/live_cd/* $HOME/live_cd/build/iso-assembly

log "Copying grub.cfg to /boot/grub/"
mkdir -p $HOME/live_cd/build/iso-assembly/boot/grub/

log "Copying grub.cfg to /boot/grub/"
cp $HOME/grub/build/boot/grub.cfg $HOME/live_cd/build/iso-assembly/boot/grub/

log "Copying initramfs.img to /boot/"
cp $HOME/initramfs/build/initramfs.img $HOME/live_cd/build/iso-assembly/boot/

log "Copying busybox"
cp -rv $HOME/busybox/build/bin $HOME/live_cd/build/iso-assembly
cp -rv $HOME/busybox/build/sbin $HOME/live_cd/build/iso-assembly
cp -rv $HOME/busybox/build/usr $HOME/live_cd/build/iso-assembly

sudo grub-mkrescue -o $HOME/$ISO_NAME $HOME/live_cd/build/iso-assembly 2>&1 | tee iso_creation.log || { log "ISO creation failed"; exit 1; }

log "Bootable ISO created successfully."

log "Finished ISO creation process."
