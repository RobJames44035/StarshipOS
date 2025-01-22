#!/bin/bash

#
# StarshipOS Copyright (c) 2025. R.A. James
#

# Ensure the script exits on unexpected errors
set -e

# Variables (modify as needed)
QCOW2_IMAGE="buildroot/buildroot/output/images/rootfs.qcow2"
QCOW2_SIZE="40G"
ROOTFS_TAR="buildroot/buildroot/output/images/rootfs.tar"
ROOTFS_IMAGE="buildroot/buildroot/output/images/rootfs.ext4"
KERNEL_IMAGE="buildroot/buildroot/output/images/bzImage"
GRUB_TIMEOUT=15

# Helper function to ensure cleanup on exit
cleanup() {
    echo "Cleaning up..."
    if mountpoint -q /mnt/qcow2; then
        umount /mnt/qcow2 || { echo "ERROR: Could not umount /mnt/qcow2"; exit 1; }
    fi
    if lsblk | grep -q "nbd0"; then
        qemu-nbd --disconnect /dev/nbd0 || { echo "ERROR: Could not qemu-nbd --disconnect /dev/nbd0"; exit 1; }
    fi
    rm -rf /mnt/qcow2
}

# Trap for cleanup on exit
trap cleanup EXIT

# Step 1: Create the QCOW2 Image
echo "Creating QCOW2 image..."
rm "$QCOW2_IMAGE"
qemu-img create -f qcow2 "$QCOW2_IMAGE" "$QCOW2_SIZE" || { echo "ERROR: Could not create disk image"; exit 1; }

# Step 2: Connect QCOW2 to a network block device (nbd)
echo "Connecting QCOW2 image to /dev/nbd0..."
modprobe nbd
qemu-nbd --connect=/dev/nbd0 "$QCOW2_IMAGE" || { echo "ERROR: Could not connect=/dev/nbd0"; exit 1; }

# Step 3: Format the QCOW2 Image
echo "Partitioning and formatting the QCOW2 image..."
echo -e "o\nn\np\n1\n\n\nw" | fdisk /dev/nbd0 || { echo "ERROR: Could not create and/or format partition(s)"; exit 1; }
mkfs.ext4 /dev/nbd0p1 || { echo "ERROR: Could not make ext4 filesystem"; exit 1; }

# Step 4: Mount the Formatted QCOW2 Image
echo "Mounting the QCOW2 image..."
mkdir -p /mnt/qcow2
mount /dev/nbd0p1 /mnt/qcow2 || { echo "ERROR: Could not mount /dev/nbd0p1 /mnt/qcow2"; exit 1; }

# Step 5: Extract the Root Filesystem
if [[ -f "$ROOTFS_TAR" ]]; then
    echo "Extracting root filesystem from $ROOTFS_TAR..."
    tar -xpf "$ROOTFS_TAR" -C /mnt/qcow2
elif [[ -f "$ROOTFS_IMAGE" ]]; then
    echo "Copying root filesystem from $ROOTFS_IMAGE..."
    dd if="$ROOTFS_IMAGE" of=/dev/nbd0p1 bs=4M
else
    echo "Error: No rootfs.tar or rootfs.ext4 found!"
    exit 1
fi

sync

# Step 6: Install GRUB
echo "Installing GRUB bootloader..."
grub-install --target=i386-pc /dev/nbd0

# Step 7: Create GRUB Configuration
echo "Configuring GRUB..."
cat <<EOF > /mnt/qcow2/boot/grub/grub.cfg
set default=0
set timeout=$GRUB_TIMEOUT

menuentry "StarshipOS" {
    linux /boot/bzImage root=/dev/sda1 rw waitroot console=ttyS0
}
EOF

sync

# Step 8: Cleanup
echo "Finalizing and unmounting QCOW2..."
sudo umount /mnt/qcow2
qemu-nbd --disconnect /dev/nbd0
rm -rf /mnt/qcow2
echo "QCOW2 image $QCOW2_IMAGE created successfully!"

echo "Launching QEMU with $QCOW2_IMAGE..."
sudo chown "$(whoami)":"$(whoami)" "$QCOW2_IMAGE" || { echo "ERROR: Could not fix ownership"; exit 1; }
#qemu-system-x86_64 -drive file="$QCOW2_IMAGE" -m 2048 -cpu host -smp 2 || { echo "ERROR: See starship-os.log"; exit 1; }
