#!/bin/bash
#
# StarshipOS Bootable SD Card Creator (Manual Selection)
# Allows user to choose the SD card device safely with no autodetection.
#
SDCARD=""
KERNEL_IMG="../buildroot/buildroot/output/images/bzImage"
ROOT_FS="../buildroot/buildroot/output/images/rootfs.ext4"
MNT_ROOT="/mnt/rootfs"
MNT_EFI="/mnt/efi"
MNT_USB="/mnt/usb"

echo "Creating a bootable SD image for StarshipOS."
# =====================================================================
# Helper Function: Exit on error and cleanup mounts or loops
# =====================================================================
cleanup_and_exit() {
    echo "Cleaning up..."
    [[ -n "$LOOPDEV" ]] && sudo losetup -d "$LOOPDEV" 2>/dev/null
    [[ -d /mnt/rootfs ]] && sudo umount /mnt/rootfs 2>/dev/null && rmdir /mnt/rootfs
    [[ -d /mnt/efi ]] && sudo umount /mnt/efi 2>/dev/null && rmdir /mnt/efi
    [[ -d /mnt/usb ]] && sudo umount /mnt/usb 2>/dev/null && rmdir /mnt/usb
    exit "$1"
}

# =====================================================================
# Step 1: List available devices and let the user select
# =====================================================================
echo "Detecting available /dev/sdX devices..."
AVAILABLE_DEVICES=$(lsblk -ndo NAME,SIZE,TYPE | grep "disk" | awk '{print "/dev/"$1, "-", $2}')

if [[ -z "$AVAILABLE_DEVICES" ]]; then
    echo "No disk devices detected. Please insert the SD card and try again."
    cleanup_and_exit 1
fi

echo "Available disk devices:"
echo "$AVAILABLE_DEVICES"
echo "Enter the device path of the SD card you wish to use (e.g., /dev/sdb):"
read -p "" SDCARD
if [[ ! -b "$SDCARD" ]]; then
    echo "Invalid device path! Please provide a valid block device (e.g., /dev/sdb)."
    cleanup_and_exit 1
fi

echo "You selected $SDCARD."

# =====================================================================
# Step 2: Confirm and Partition the SD Card
# =====================================================================
echo "WARNING: This process will ERASE ALL DATA on $SDCARD."
echo "Are you sure you want to continue? [y/N]:"
read -p "" CONFIRM
if [[ "$CONFIRM" != "y" && "$CONFIRM" != "Y" ]]; then
    echo "Operation cancelled."
    cleanup_and_exit 0
fi

echo "Partitioning $SDCARD as GPT with EFI, rootfs, and swap partitions..."
sudo parted "$SDCARD" --script mklabel gpt
sudo parted "$SDCARD" --script mkpart primary fat32 1MiB 513MiB
sudo parted "$SDCARD" --script set 1 esp on
sudo parted "$SDCARD" --script mkpart primary ext4 513MiB 100%
sudo parted "$SDCARD" print
echo "Is this correct?"
read -p "" CONFIRM
if [[ "$CONFIRM" != "y" && "$CONFIRM" != "Y" ]]; then
    echo "Operation cancelled."
    cleanup_and_exit 0
fi

echo "Formatting partitions..."
sudo mkfs.vfat -F 32 -n EFI "${SDCARD}"1
sudo mkfs.ext4 -L rootfs "${SDCARD}"2

# =====================================================================
# Step 3: Mount the Partitions
# =====================================================================
echo "Setting up a loop device for $KERNEL_IMG..."
LOOPDEV=$(sudo losetup -fP --show "$ROOT_FS")
if [[ -z "$LOOPDEV" ]]; then
    echo "Failed to create a loop device for $ROOT_FS"
    cleanup_and_exit 1
fi

echo "Mounting filesystems."
sudo mkdir -p "$MNT_EFI" "$MNT_USB" "$MNT_ROOT"
sudo mount -o loop $ROOT_FS $MNT_ROOT
sudo mount "${SDCARD}"1 $MNT_EFI
sudo mount "${SDCARD}"2 $MNT_USB

sudo mkdir -p "/mnt/rootfs/boot/grub"
echo "Copying $MNT_ROOT to $MNT_USB"
sudo rsync -avh "$MNT_ROOT/" "$MNT_USB"
sudo cp -pv "$KERNEL_IMG" "$MNT_ROOT/boot"

# =====================================================================
# Step 5: Install GRUB for UEFI Boot
# =====================================================================
echo "Installing GRUB bootloader for UEFI..."

sudo grub-install --target=x86_64-efi \
  --efi-directory=$MNT_EFI \
  --boot-directory=$MNT_USB/boot \
  --removable \
  --recheck "$SDCARD" || cleanup_and_exit 1

echo "Creating GRUB configuration..."
sudo tee $MNT_USB/boot/grub/grub.cfg > /dev/null <<EOF
set default=0
set timeout=15

menuentry "StarshipOS" {
    set root=(hd0,gpt2)
    linux /boot/bzImage root=/dev/sdb2 rw console=ttyS0 init=/init
}
EOF

# =====================================================================
# Step 6: Unmount Partitions and Finalize
# =====================================================================
echo "Unmounting partitions and finalizing setup..."
sudo umount $MNT_EFI $MNT_ROOT $MNT_USB
sudo rmdir $MNT_EFI $MNT_ROOT $MNT_USB

echo "Bootable SD card with a 10GB swap partition has been successfully created on $SDCARD!"

# =====================================================================
# Step 7: Reboot the System
# =====================================================================
echo "Do you want to reboot now and boot from the SD card? [y/N]:"
read -p "" REBOOT_CONFIRM
if [[ "$REBOOT_CONFIRM" == "y" || "$REBOOT_CONFIRM" == "Y" ]]; then
    echo "Rebooting system..."
    sudo reboot
else
    echo "You can now boot from the SD card by restarting your system and selecting it in the UEFI boot menu."
    cleanup_and_exit 0
fi
