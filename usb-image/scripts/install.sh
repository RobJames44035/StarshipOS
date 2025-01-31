#!/bin/bash

# StarshipOS Bootable SD Card Creator (UEFI EFISTUB Version)

SDCARD=""
KERNEL_IMG="../buildroot/buildroot/output/images/bzImage"
ROOT_FS="../buildroot/buildroot/output/images/rootfs.ext4"
MNT_ROOT="/mnt/rootfs"
MNT_SD_ROOT="/mnt/sd_root"
MNT_EFI="/mnt/efi"
LOOP_DEVICE=""

echo "Creating a bootable SD image for StarshipOS."

# =====================================================================
# Helper Function: Cleanup on Exit
# =====================================================================
cleanup_and_exit() {
    echo "Cleaning up..."
    if [[ -n "$LOOP_DEVICE" ]]; then
        if mountpoint -q "$MNT_ROOT"; then
            sudo umount "$MNT_ROOT"
        fi
        echo "Detaching loop device $LOOP_DEVICE..."
        sudo losetup -d "$LOOP_DEVICE"
        LOOP_DEVICE=""
    fi
    if mountpoint -q "$MNT_SD_ROOT"; then
        sudo umount "$MNT_SD_ROOT"
    fi
    if mountpoint -q "$MNT_EFI"; then
        sudo umount "$MNT_EFI"
    fi
    [[ -d "$MNT_ROOT" ]] && sudo rmdir "$MNT_ROOT"
    [[ -d "$MNT_SD_ROOT" ]] && sudo rmdir "$MNT_SD_ROOT"
    [[ -d "$MNT_EFI" ]] && sudo rmdir "$MNT_EFI"
    exit "$1"
}

# Trap errors and perform cleanup
trap 'cleanup_and_exit 1' ERR

# Exit script on any unhandled error
set -e

# =====================================================================
# Step 1: Prompt the user for the device
# =====================================================================
echo "Manually select an SD card device (e.g., /dev/sdX):"
read -p "Device: " SDCARD

if [[ ! -b "$SDCARD" ]]; then
    echo "Invalid device path! Please provide a valid block device (e.g., /dev/sdb)."
    cleanup_and_exit 1
fi

echo "You selected $SDCARD. Note: THIS WILL DELETE ALL DATA ON IT!"
read -p "Are you sure you want to continue? [y/N]: " CONFIRM
if [[ "$CONFIRM" != "y" && "$CONFIRM" != "Y" ]]; then
    echo "Operation cancelled."
    cleanup_and_exit 0
fi

# =====================================================================
# Step 2: Unmount Mounted Partitions and Wipe
# =====================================================================
echo "Checking for mounted partitions on $SDCARD..."
MOUNTED_PARTITIONS=$(lsblk -ndo NAME,MOUNTPOINT "$SDCARD" | awk '$2!="" {print $1}')

if [[ -n "$MOUNTED_PARTITIONS" ]]; then
    echo "The following partitions are mounted and will be unmounted:"
    for PART in $MOUNTED_PARTITIONS; do
        echo "Unmounting /dev/$PART..."
        sudo umount "/dev/$PART" || { echo "Error: Failed to unmount /dev/$PART"; cleanup_and_exit 1; }
    done
else
    echo "No mounted partitions found on $SDCARD."
fi

echo "Wiping existing partitions on $SDCARD..."
sudo wipefs -a "$SDCARD" || { echo "Error: Failed to wipe $SDCARD"; cleanup_and_exit 1; }

# =====================================================================
# Step 3: Partition and Format the SD Card
# =====================================================================
echo "Partitioning $SDCARD as GPT with EFI and rootfs partitions..."
sudo parted "$SDCARD" --script mklabel gpt
sudo parted "$SDCARD" --script mkpart primary fat32 1MiB 513MiB
sudo parted "$SDCARD" --script set 1 esp on
sudo parted "$SDCARD" --script mkpart primary ext4 513MiB 100%

echo "Formatting partitions..."
sudo mkfs.vfat -F 32 -n EFI "${SDCARD}1"
sudo mkfs.ext4 -L rootfs "${SDCARD}2"

# =====================================================================
# Step 4: Mount the partitions and loop device
# =====================================================================
echo "Mounting file systems..."
sudo mkdir -p "$MNT_EFI" "$MNT_SD_ROOT" "$MNT_ROOT"
sudo mount "${SDCARD}1" "$MNT_EFI" # Mount EFI partition
sudo mount "${SDCARD}2" "$MNT_SD_ROOT" # Mount SD root partition

echo "Setting up loop device for $ROOT_FS..."
LOOP_DEVICE=$(sudo losetup --find --show "$ROOT_FS")
if [[ -z "$LOOP_DEVICE" ]]; then
    echo "Error: Failed to create and attach loop device for $ROOT_FS."
    cleanup_and_exit 1
fi
echo "Root filesystem mounted via loop device: $LOOP_DEVICE"

# Mount the `rootfs.ext4` filesystem for reading
sudo mount "$LOOP_DEVICE" "$MNT_ROOT"

# =====================================================================
# Step 5: Rsync root filesystem to SD card root partition
# =====================================================================
echo "Copying root filesystem from $MNT_ROOT to SD card root partition ($MNT_SD_ROOT)..."
sudo rsync -aHAXv "$MNT_ROOT/" "$MNT_SD_ROOT/" --delete

# =====================================================================
# Step 6: Add Kernel to /boot
# =====================================================================
echo "Copying kernel to /boot directory in SD card root partition..."
sudo mkdir -p "$MNT_SD_ROOT/boot"
sudo cp -v "$KERNEL_IMG" "$MNT_SD_ROOT/boot/bzImage"

# =====================================================================
# Step 7: Pause for Inspection
# =====================================================================
echo "Filesystem copied. You can now inspect the SD card root partition at $MNT_SD_ROOT."
read -p "Press [Enter] to finalize and unmount everything..."

# =====================================================================
# Step 8: Unmount and Cleanup
# =====================================================================
echo "Finalizing changes..."
sudo umount "$MNT_ROOT"
#sudo losetup -d "$LOOP_DEVICE"
sudo umount "$MNT_SD_ROOT"
sudo umount "$MNT_EFI"

sudo rmdir "$MNT_EFI" "$MNT_SD_ROOT" "$MNT_ROOT"

echo "Bootable SD card creation complete! You can now boot from it."
cleanup_and_exit 0