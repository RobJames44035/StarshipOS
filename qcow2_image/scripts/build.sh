#!/bin/bash

# Exit on errors or unset variables
set -e
set -u

# Directories and configuration
HOME="/home/rajames/PROJECTS/StarshipOS"
QCOW2_DIR="$HOME/qcow2_image"
BUILD_DIR="$QCOW2_DIR/build"
HDD_NAME="starship-os.qcow2"
HDD_SIZE="40G"
BOOT_MOUNT="$BUILD_DIR/boot"
ROOT_MOUNT="$BUILD_DIR/mnt"

# Cleanup function
cleanup() {
    sudo umount "$BOOT_MOUNT" &>/dev/null || true
    sudo umount "$ROOT_MOUNT" &>/dev/null || true
    [[ -n "${LOOP_DEVICE:-}" ]] && sudo losetup -d "$LOOP_DEVICE" &>/dev/null || true
    rm -rf "$BOOT_MOUNT" "$ROOT_MOUNT"
}
trap cleanup EXIT

function log() {
    echo "[$(date +'%Y-%m-%d %H:%M:%S')] $1"
}

log "Step 1: Create QCOW2 image"
mkdir -p "$BUILD_DIR"
if [ ! -f "$BUILD_DIR/$HDD_NAME" ]; then
    log "Creating QCOW2 disk image ($HDD_NAME) of size $HDD_SIZE."
    qemu-img create -f qcow2 "$BUILD_DIR/$HDD_NAME" "$HDD_SIZE"
fi

# Convert QCOW2 to raw for block device compatibility
RAW_FILE="$BUILD_DIR/starship-os.raw"
qemu-img convert -f qcow2 -O raw "$BUILD_DIR/$HDD_NAME" "$RAW_FILE"

# Attach raw image to a loopback device
LOOP_DEVICE=$(sudo losetup --find --show --partscan "$RAW_FILE")

log "Step 2: Partition the disk (including BIOS Boot Partition)"
sudo parted -s "$LOOP_DEVICE" mklabel gpt
sudo parted -s "$LOOP_DEVICE" mkpart primary 1MiB 2MiB     # BIOS Boot Partition
sudo parted -s "$LOOP_DEVICE" set 1 bios_grub on          # Mark as BIOS Boot
sudo parted -s "$LOOP_DEVICE" mkpart primary ext4 2MiB 1024MiB # Boot Partition
sudo parted -s "$LOOP_DEVICE" mkpart primary ext4 1024MiB 100% # Root Partition
sudo partprobe "$LOOP_DEVICE"

log "Step 3: Format the boot and root partitions"
BOOT_PART="${LOOP_DEVICE}p2"
ROOT_PART="${LOOP_DEVICE}p3"
sudo mkfs.ext4 "$BOOT_PART"
sudo mkfs.ext4 "$ROOT_PART"

log "Step 4: Mount the partitions"
mkdir -p "$BOOT_MOUNT" "$ROOT_MOUNT"
sudo mount "$BOOT_PART" "$BOOT_MOUNT"
sudo mount "$ROOT_PART" "$ROOT_MOUNT"

# Ensure the required directories for the bootloader exist
sudo mkdir -p "$BOOT_MOUNT/boot"               # Create /boot if not already created
sudo mkdir -p "$BOOT_MOUNT/boot/grub"          # Create /boot/grub for GRUB files

# Copy the kernel to the boot partition
sudo cp -p "./target/kernel/build/init_ram_fs/boot/starship" "$BOOT_MOUNT/boot/starship"
# Copy libraries to the boot partition
sudo cp -rp "./target/kernel/build/init_ram_fs/lib" "$BOOT_MOUNT/lib"

# Copy the initramfs to the boot partition
sudo cp -p "./target/ramdisks/build/initramfs.hd0.gz" "$BOOT_MOUNT/boot/initramfs.hd0.gz"

# Copy GRUB configuration file
sudo cp -p "./target/grub/hd0/init_ram_fs/boot/grub.cfg" "$BOOT_MOUNT/boot/grub/grub.cfg"
# Install GRUB on the device
log "Installing GRUB..."
sudo grub-install --target=i386-pc --boot-directory="$BOOT_MOUNT" "$LOOP_DEVICE"

log "Step 6: Populate the root filesystem"
log "Populating root filesystem with necessary directories."
sudo mkdir -p "$ROOT_MOUNT/bin"
sudo mkdir -p "$ROOT_MOUNT/dev"
sudo mkdir -p "$ROOT_MOUNT/etc"
sudo mkdir -p "$ROOT_MOUNT/home"
sudo mkdir -p "$ROOT_MOUNT/lib"
sudo mkdir -p "$ROOT_MOUNT/lib64"
sudo mkdir -p "$ROOT_MOUNT/mnt"
sudo mkdir -p "$ROOT_MOUNT/opt"
sudo mkdir -p "$ROOT_MOUNT/proc"
sudo mkdir -p "$ROOT_MOUNT/root"
sudo mkdir -p "$ROOT_MOUNT/sbin"
sudo mkdir -p "$ROOT_MOUNT/sys"
sudo mkdir -p "$ROOT_MOUNT/tmp"
sudo mkdir -p "$ROOT_MOUNT/usr/bin"
sudo mkdir -p "$ROOT_MOUNT/usr/lib"
sudo mkdir -p "$ROOT_MOUNT/usr/lib64"
sudo mkdir -p "$ROOT_MOUNT/usr/sbin"
sudo mkdir -p "$ROOT_MOUNT/var/log"
sudo mkdir -p "$ROOT_MOUNT/var/tmp"
sudo mkdir -p "$ROOT_MOUNT/var/run"

log "Copy BusyBox binaries"
log "Copying BusyBox binaries to root filesystem."
sudo cp -rp "./target/busybox/build/init_ram_fs/bin" "$ROOT_MOUNT"
sudo cp -rp "./target/busybox/build/init_ram_fs/sbin" "$ROOT_MOUNT"

log "Adding Java JDK to root filesystem."
sudo mkdir -p "$ROOT_MOUNT/java/jdk"
sudo cp -rp "target/java/build/jdk" "$ROOT_MOUNT/java/jdk"

# Visual check of directory structure
log "Tree of boot partition:"
tree "$BOOT_MOUNT" > bootmount.txt

log "Tree of root partition:"
tree "$ROOT_MOUNT" > rootmount.txt

# Step 7: Unmount and detach
log "Unmounting partitions and detaching loop device."
sudo umount "$BOOT_MOUNT"
sudo umount "$ROOT_MOUNT"
sudo losetup -d "$LOOP_DEVICE"

# Recreate the QCOW2 file after modifications
qemu-img convert -f raw -O qcow2 "$RAW_FILE" "$BUILD_DIR/$HDD_NAME"
rm -f "$RAW_FILE"

log "Disk setup with bootloader and root filesystem completed successfully."
