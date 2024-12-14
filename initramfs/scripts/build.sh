#!/bin/bash

set -e  # Exit immediately if a command exits with a non-zero status
set -u  # Treat unset variables as an error

# Function for timestamped logging
function log() {
    echo "[$(date +'%Y-%m-%d %H:%M:%S')] $1"
#    read -p ""
}

# Set paths
BUILD_DIR="target/build"
INITRAMFS_DIR="$BUILD_DIR/init_ram_fs"
GRUB_DIR="$BUILD_DIR/grub"
OUTPUT_DIR="build"
HOME=$(pwd)

# Prepare the output directory
log "Preparing output directory at $OUTPUT_DIR"
mkdir -p "$OUTPUT_DIR"

# ----- Checkpoint 1: Prepare Directories -----
log "Starting Checkpoint 1: Preparing initramfs directories"

# Iterate through each block device directory in the grub directory
for block_device_dir in "$GRUB_DIR"/*; do
    if [ -d "$block_device_dir" ]; then
        block_device_name=$(basename "$block_device_dir")
        target_dir="$OUTPUT_DIR/initramfs_$block_device_name"
        cp -r "$INITRAMFS_DIR" "$target_dir"
        log "Copied init_ram_fs to $target_dir"

        # Ensure the boot/grub directory exists in the new structure
        mkdir -p "$target_dir/boot/grub"
        log "Created directory: $target_dir/boot/grub"

        # Copy the corresponding grub.cfg file to boot/grub in the output structure
        cp "$block_device_dir/init_ram_fs/boot/grub.cfg" "$target_dir/boot/grub/"
        log "Copied grub.cfg from $block_device_dir to $target_dir/boot/grub/"

#        cd "$HOME"

        block_device_name=$(basename "$block_device_dir" | sed 's/initramfs_//')
        initramfs_cpio_name="initramfs.$block_device_name.cpio"
        initramfs_final_name="initramfs.$block_device_name.gz"
        log "Creating $initramfs_cpio_name"
        home_dir=$(pwd)
        block_device_dir="/home/rajames/PROJECTS/StarshipOS/initramfs/build/initramfs_$block_device_name"
        log "Working Directory: $block_device_dir"
        cd "$block_device_dir"
        sudo find . -mindepth 1 -print0 | cpio --null -o -H newc --owner root:root > "$home_dir/$initramfs_cpio_name"
        gzip "$home_dir/$initramfs_cpio_name"
        mv "$home_dir/$initramfs_cpio_name.gz" "$home_dir/$initramfs_final_name"
        mv "$home_dir/$initramfs_final_name" "$home_dir/build"
        cd "$home_dir"
        rm -rf cd "$block_device_dir"
    else
        log "Skipping $block_device_dir (not a directory)"
    fi
done
