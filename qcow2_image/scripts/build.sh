#!/bin/bash

# Exit on errors or unset variables
set -e
set -u

# Directories and configuration
BUILD_DIR="build"
HDD_FILENAME="starship-os.qcow2"
HDD_SIZE="40G"
MOUNTPOINT_FOR_BOOT_FILESYSTEM="$BUILD_DIR/boot"
ROOT_FILESYSTEM_MOUNTPOINT="$BUILD_DIR/root"

# Cleanup function
cleanup() {
    sudo umount "$MOUNTPOINT_FOR_BOOT_FILESYSTEM" &>/dev/null || true
    sudo umount "$ROOT_FILESYSTEM_MOUNTPOINT" &>/dev/null || true
    [[ -n "${LOOP_DEVICE:-}" ]] && sudo losetup -d "$LOOP_DEVICE" &>/dev/null || true
    rm -rf "$MOUNTPOINT_FOR_BOOT_FILESYSTEM" "$ROOT_FILESYSTEM_MOUNTPOINT"
}
trap cleanup EXIT

function log() {
    echo "[$(date +'%Y-%m-%d %H:%M:%S')] $1"
}

log "Step 1: Create QCOW2 image"
mkdir -p "$BUILD_DIR"
if [ ! -f "$BUILD_DIR/$HDD_FILENAME" ]; then
    log "Creating QCOW2 disk image ($HDD_FILENAME) of size $HDD_SIZE."
    qemu-img create -f qcow2 "$BUILD_DIR/$HDD_FILENAME" "$HDD_SIZE"
    [ $? -eq 0 ] && echo "Done!" || { log "ERROR: $HDD_FILENAME not created"; exit 1; }
fi

# Convert QCOW2 to raw for block device compatibility
RAW_FILE="$BUILD_DIR/starship-os.raw"
qemu-img convert -f qcow2 -O raw "$BUILD_DIR/$HDD_FILENAME" "$RAW_FILE"
[ $? -eq 0 ] && echo "Done!" || { log "ERROR: $RAW_FILE not created"; exit 1; }
# Attach raw image to a loopback device
LOOP_DEVICE=$(sudo losetup --find --show --partscan "$RAW_FILE")

log "Step 2: Partition the disk (including BIOS Boot Partition)"

sudo parted -s "$LOOP_DEVICE" mklabel gpt
log "Partition Table Creation"
[ $? -eq 0 ] && echo "Done!" || { log "ERROR: parted -s $LOOP_DEVICE mklabel gpt"; exit 1; }

log "Create BIOS boot partition"
sudo parted -s "$LOOP_DEVICE" mkpart primary 1MiB 2MiB     # BIOS Boot Partition
[ $? -eq 0 ] && echo "Done!" || { log "ERROR: parted -s $LOOP_DEVICE mkpart primary 1MiB 2MiB"; exit 1; }

log "Mark BIOS partition as BIOS boot"
sudo parted -s "$LOOP_DEVICE" set 1 bios_grub on          # Mark as BIOS Boot
[ $? -eq 0 ] && echo "Done!" || { log "ERROR: parted -s $LOOP_DEVICE set 1 bios_grub on"; exit 1; }

log "Create Linux /boot partition"
sudo parted -s "$LOOP_DEVICE" mkpart primary ext4 2MiB 1024MiB # Boot Partition
[ $? -eq 0 ] && echo "Done!" || { log "ERROR: not created"; exit 1; }

log "Create Linus / (root) partition"
sudo parted -s "$LOOP_DEVICE" mkpart primary ext4 1024MiB 100% # Root Partition
[ $? -eq 0 ] && echo "Done!" || { log "ERROR: not created"; exit 1; }

sudo partprobe "$LOOP_DEVICE"
[ $? -eq 0 ] && echo "Done!" || { log "ERROR: $LOOP_DEVICE not available"; exit 1; }

log "Step 3: Format the boot and root partitions"

log "Formatting ${LOOP_DEVICE}p2"
sudo mkfs.ext4 "${LOOP_DEVICE}p2"
[ $? -eq 0 ] && echo "Done!" || { log "ERROR: formatting ${LOOP_DEVICE}p2"; exit 1; }

log "Formatting ${LOOP_DEVICE}p3"
sudo mkfs.ext4 "${LOOP_DEVICE}p3"
[ $? -eq 0 ] && echo "Done!" || { log "ERROR: formatting ${LOOP_DEVICE}p3"; exit 1; }

log "Step 4: Mount the partitions"
log "Create mountpoint directories for $MOUNTPOINT_FOR_BOOT_FILESYSTEM and $ROOT_FILESYSTEM_MOUNTPOINT"
mkdir -p "$MOUNTPOINT_FOR_BOOT_FILESYSTEM" "$ROOT_FILESYSTEM_MOUNTPOINT"
[ $? -eq 0 ] && echo "Done!" || { log "ERROR: in mkdir -p $MOUNTPOINT_FOR_BOOT_FILESYSTEM and $ROOT_FILESYSTEM_MOUNTPOINT"; exit 1; }

log "Mount $MOUNTPOINT_FOR_BOOT_FILESYSTEM"
sudo mount "${LOOP_DEVICE}p2" "$MOUNTPOINT_FOR_BOOT_FILESYSTEM"
[ $? -eq 0 ] && echo "Done!" || { log "ERROR: mounting $MOUNTPOINT_FOR_BOOT_FILESYSTEM"; cleanup; }

log "Mount $ROOT_FILESYSTEM_MOUNTPOINT"
sudo mount "${LOOP_DEVICE}p3" "$ROOT_FILESYSTEM_MOUNTPOINT"
[ $? -eq 0 ] && echo "Done!" || { log "ERROR: mounting $ROOT_FILESYSTEM_MOUNTPOINT"; cleanup; }

# Ensure the required directories for the bootloader exist
log "Ensure the required directories for the bootloader exist"

log "mkdir -p $MOUNTPOINT_FOR_BOOT_FILESYSTEM/boot"
sudo mkdir -p "$MOUNTPOINT_FOR_BOOT_FILESYSTEM/boot"
[ $? -eq 0 ] && echo "Done!" || { log "ERROR: mkdir -p $MOUNTPOINT_FOR_BOOT_FILESYSTEM/boot"; cleanup; }

log "mkdir -p $MOUNTPOINT_FOR_BOOT_FILESYSTEM/boot/grub"
sudo mkdir -p "$MOUNTPOINT_FOR_BOOT_FILESYSTEM/boot/grub"
[ $? -eq 0 ] && echo "Done!" || { log "ERROR: mkdir -p $MOUNTPOINT_FOR_BOOT_FILESYSTEM/boot/grub"; cleanup; }

# Copy the kernel to the boot partition
log "Copy the kernel to the /boot/starship"
sudo cp -p "./target/kernel/build/init_ram_fs/boot/starship" "$MOUNTPOINT_FOR_BOOT_FILESYSTEM/boot/starship"
[ $? -eq 0 ] && echo "Done!" || { log "ERROR: copying the kernel to /boot/starship"; cleanup; }

# Copy libraries to the boot partition
log "Copy kernel modules to the boot partition"
sudo cp -rp "./target/kernel/build/init_ram_fs/lib" "$MOUNTPOINT_FOR_BOOT_FILESYSTEM/lib"
[ $? -eq 0 ] && echo "Done!" || { log "ERROR: copying the kernel modules to /boot/lib"; cleanup; }

# Copy the initramfs to the boot partition
log "Copy initrd.gz to the boot partition"
sudo cp -p "./target/ramdisks/build/initrd.gz" "$MOUNTPOINT_FOR_BOOT_FILESYSTEM/boot/initrd.gz"
[ $? -eq 0 ] && echo "Done!" || { log "ERROR: copying initrd.gz to $MOUNTPOINT_FOR_BOOT_FILESYSTEM/boot/initrd.gz"; cleanup; }

# Copy GRUB configuration file
log "Copy GRUB configuration file."
sudo cp -p "target/grub/hd0/init_ram_fs/boot/grub/grub.cfg" "$MOUNTPOINT_FOR_BOOT_FILESYSTEM/boot/grub/grub.cfg"
[ $? -eq 0 ] && echo "Done!" || { log "ERROR: copying initrd.gz to $MOUNTPOINT_FOR_BOOT_FILESYSTEM/boot/initrd.gz"; cleanup; }

# Install GRUB on the device
log "Installing GRUB..."
sudo grub-install --target=i386-pc --boot-directory="$MOUNTPOINT_FOR_BOOT_FILESYSTEM" "$LOOP_DEVICE"
[ $? -eq 0 ] && echo "Done!" || { log "ERROR: installing GRUB"; cleanup; }

log "Step 6: Populate the root filesystem"
log "Populating root filesystem with necessary directories."
sudo mkdir -p "$ROOT_FILESYSTEM_MOUNTPOINT/bin"; [ $? -eq 0 ] && echo "Done!" || { log "ERROR"; cleanup; }
sudo mkdir -p "$ROOT_FILESYSTEM_MOUNTPOINT/dev"; [ $? -eq 0 ] && echo "Done!" || { log "ERROR"; cleanup; }
sudo mkdir -p "$ROOT_FILESYSTEM_MOUNTPOINT/etc"; [ $? -eq 0 ] && echo "Done!" || { log "ERROR"; cleanup; }
sudo mkdir -p "$ROOT_FILESYSTEM_MOUNTPOINT/home"; [ $? -eq 0 ] && echo "Done!" || { log "ERROR"; cleanup; }
sudo mkdir -p "$ROOT_FILESYSTEM_MOUNTPOINT/lib"; [ $? -eq 0 ] && echo "Done!" || { log "ERROR"; cleanup; }
sudo mkdir -p "$ROOT_FILESYSTEM_MOUNTPOINT/lib64"; [ $? -eq 0 ] && echo "Done!" || { log "ERROR"; cleanup; }
sudo mkdir -p "$ROOT_FILESYSTEM_MOUNTPOINT/mnt"; [ $? -eq 0 ] && echo "Done!" || { log "ERROR"; cleanup; }
sudo mkdir -p "$ROOT_FILESYSTEM_MOUNTPOINT/opt"; [ $? -eq 0 ] && echo "Done!" || { log "ERROR"; cleanup; }
sudo mkdir -p "$ROOT_FILESYSTEM_MOUNTPOINT/proc"; [ $? -eq 0 ] && echo "Done!" || { log "ERROR"; cleanup; }
sudo mkdir -p "$ROOT_FILESYSTEM_MOUNTPOINT/root"; [ $? -eq 0 ] && echo "Done!" || { log "ERROR"; cleanup; }
sudo mkdir -p "$ROOT_FILESYSTEM_MOUNTPOINT/sbin"; [ $? -eq 0 ] && echo "Done!" || { log "ERROR"; cleanup; }
sudo mkdir -p "$ROOT_FILESYSTEM_MOUNTPOINT/sys"; [ $? -eq 0 ] && echo "Done!" || { log "ERROR"; cleanup; }
sudo mkdir -p "$ROOT_FILESYSTEM_MOUNTPOINT/tmp"; [ $? -eq 0 ] && echo "Done!" || { log "ERROR"; cleanup; }
sudo mkdir -p "$ROOT_FILESYSTEM_MOUNTPOINT/usr/bin"; [ $? -eq 0 ] && echo "Done!" || { log "ERROR"; cleanup; }
sudo mkdir -p "$ROOT_FILESYSTEM_MOUNTPOINT/usr/lib"; [ $? -eq 0 ] && echo "Done!" || { log "ERROR"; cleanup; }
sudo mkdir -p "$ROOT_FILESYSTEM_MOUNTPOINT/usr/lib64"; [ $? -eq 0 ] && echo "Done!" || { log "ERROR"; cleanup; }
sudo mkdir -p "$ROOT_FILESYSTEM_MOUNTPOINT/usr/sbin"; [ $? -eq 0 ] && echo "Done!" || { log "ERROR"; cleanup; }
sudo mkdir -p "$ROOT_FILESYSTEM_MOUNTPOINT/var/log"; [ $? -eq 0 ] && echo "Done!" || { log "ERROR"; cleanup; }
sudo mkdir -p "$ROOT_FILESYSTEM_MOUNTPOINT/var/tmp"; [ $? -eq 0 ] && echo "Done!" || { log "ERROR"; cleanup; }
sudo mkdir -p "$ROOT_FILESYSTEM_MOUNTPOINT/var/run"; [ $? -eq 0 ] && echo "Done!" || { log "ERROR"; cleanup; }

log "Copying BusyBox to $ROOT_FILESYSTEM_MOUNTPOINT."
sudo cp -rp "./target/busybox/build/init_ram_fs/bin" "$ROOT_FILESYSTEM_MOUNTPOINT"; [ $? -eq 0 ] && echo "Done!" || { log "ERROR"; cleanup; }
sudo cp -rp "./target/busybox/build/init_ram_fs/sbin" "$ROOT_FILESYSTEM_MOUNTPOINT"; [ $? -eq 0 ] && echo "Done!" || { log "ERROR"; cleanup; }

log "Adding Java JDK to root filesystem."
sudo mkdir -p "$ROOT_FILESYSTEM_MOUNTPOINT/java/jdk"; [ $? -eq 0 ] && echo "Done!" || { log "ERROR"; cleanup; }
sudo cp -rp "target/java/build/jdk" "$ROOT_FILESYSTEM_MOUNTPOINT/java/jdk"; [ $? -eq 0 ] && echo "Done!" || { log "ERROR"; cleanup; }

# Check of directory structure. These are in git and available for inspections and diff's
log "Check of directory structure. These are in git and available for inspections and diff's"
tree "$MOUNTPOINT_FOR_BOOT_FILESYSTEM" > bootmount.txt
tree "$ROOT_FILESYSTEM_MOUNTPOINT" > rootmount.txt

# Step 7: Unmount and detach
log "Unmounting partitions and detaching loop device."
sudo umount "$MOUNTPOINT_FOR_BOOT_FILESYSTEM"; [ $? -eq 0 ] && echo "Done!" || { log "ERROR: unmounting $MOUNTPOINT_FOR_BOOT_FILESYSTEM"; cleanup; }
sudo umount "$ROOT_FILESYSTEM_MOUNTPOINT"; [ $? -eq 0 ] && echo "Done!" || { log "ERROR: unmounting $ROOT_FILESYSTEM_MOUNTPOINT"; cleanup; }
sudo losetup -d "$LOOP_DEVICE"; [ $? -eq 0 ] && echo "Done!" || { log "ERROR: disconnecting $LOOP_DEVICE"; cleanup; }

# Recreate the QCOW2 file after modifications
log "Recreate the QCOW2 file after modifications"
qemu-img convert -f raw -O qcow2 "$RAW_FILE" "$BUILD_DIR/$HDD_FILENAME"
[ $? -eq 0 ] && echo "Done!" || { log "ERROR: recreating the QCOW2 file after modifications"; EXIT 1; }
rm -f "$RAW_FILE"
[ $? -eq 0 ] && echo "Done!" || { log "ERROR"; EXIT 1; }

log "Disk setup with bootloader and root filesystem completed successfully."
