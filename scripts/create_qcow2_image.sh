#!/bin/bash
#
# StarshipOS Copyright (c) 2025. R.A. James
#

# Ensure the script exits on unexpected errors
set -e

# Variables (modify as needed)
QCOW2_IMAGE="buildroot/buildroot/output/images/rootfs.qcow2"
QCOW2_SIZE="80G"
ROOTFS_TAR="buildroot/buildroot/output/images/rootfs.tar"
KERNEL_IMAGE="buildroot/buildroot/output/images/bzImage"
GRUB_TIMEOUT=15

# Helper function to ensure cleanup on exit
cleanup() {
    echo "Cleaning up..."
    if mountpoint -q /mnt/qcow2; then
        sudo umount /mnt/qcow2 || { echo "ERROR: Could not unmount /mnt/qcow2"; exit 1; }
    fi
    if lsblk | grep -q "nbd0"; then
        sudo qemu-nbd --disconnect /dev/nbd0 || { echo "ERROR: Could not disconnect /dev/nbd0"; exit 1; }
    fi
    sudo rm -rf /mnt/qcow2
}
trap cleanup EXIT

# Step 1: Create the QCOW2 Image
echo "Creating QCOW2 image..."
qemu-img create -f qcow2 "$QCOW2_IMAGE" "$QCOW2_SIZE" || { echo "ERROR: Could not create QCOW2 image"; exit 1; }

# Step 2: Connect QCOW2 to NBD
echo "Connecting QCOW2 image to /dev/nbd0..."
sudo modprobe nbd
sudo qemu-nbd --connect=/dev/nbd0 "$QCOW2_IMAGE" || { echo "ERROR: Could not connect QCOW2 to /dev/nbd0"; exit 1; }

# Step 3: Partition and Format the QCOW2 Image
echo "Partitioning and formatting the QCOW2 image with parted..."

# Create a GPT partition table
sudo parted -s /dev/nbd0 mklabel gpt || { echo "ERROR: Could not create partition table"; exit 1; }

# Create a BIOS boot partition (1 MiB, no filesystem needed)
sudo parted -s /dev/nbd0 mkpart primary 1MiB 2MiB || { echo "ERROR: Could not create BIOS Boot Partition"; exit 1; }
sudo parted -s /dev/nbd0 set 1 bios_grub on || { echo "ERROR: Could not set BIOS Boot Partition flag"; exit 1; }

# Create the primary ext4 root partition (remaining space)
sudo parted -s /dev/nbd0 mkpart primary ext4 2MiB 100% || { echo "ERROR: Could not create root partition"; exit 1; }

# Inform the kernel of partition table changes
sudo partprobe /dev/nbd0 || sleep 2

# Format the root partition as ext4
sudo mkfs.ext4 /dev/nbd0p2 || { echo "ERROR: Could not format root partition as ext4"; exit 1; }

# Step 4: Mount the Formatted Partition
echo "Mounting the partition..."
sudo mkdir -p /mnt/qcow2
sudo fsck.ext4 /dev/nbd0p2  # Check the root partition instead
sudo mount /dev/nbd0p2 /mnt/qcow2 || { echo "ERROR: Could not mount /dev/nbd0p2"; exit 1; }

# Step 5: Extract Root Filesystem and Add /init Script
echo "Extracting root filesystem from $ROOTFS_TAR..."
sudo tar -xpf "$ROOTFS_TAR" -C /mnt/qcow2 || { echo "ERROR: Could not extract root filesystem"; exit 1; }

# Add the /init script
echo "Adding custom /init script..."
cat <<'EOF' | sudo tee /mnt/qcow2/init > /dev/null
#!/bin/sh

# Set debugging options (optional)
set -e
set -x

# Mount essential filesystems
mount -t proc none /proc
mount -t sysfs none /sys
mount -t devtmpfs none /dev
mdev -s  # Populate /dev using mdev

# Console setup
[ -c /dev/console ] || mknod /dev/console c 5 1
exec </dev/console >/dev/console 2>/dev/console

echo "Init script: Setting up devices and consoles."

# Placeholder: Additional setup tasks
# Add specific device setup commands or filesystem mounting if required
echo "Custom setup done."

# Execute the custom init process
exec /bin/sh
EOF

# Ensure /init has the correct permissions
sudo mount -o remount,rw /mnt/qcow2
sudo chmod +x /mnt/qcow2/init
sudo chown root:root /mnt/qcow2/init
echo "/init script added successfully!"

# Sync changes to disk
sync

# Step 6: Install GRUB
echo "Installing GRUB bootloader..."
sudo grub-install --target=i386-pc /dev/nbd0 --boot-directory=/mnt/qcow2/boot || { echo "ERROR: GRUB installation failed"; exit 1; }

# Step 7: Configure GRUB
echo "Configuring GRUB menu..."
cat <<'EOF' | sudo tee /mnt/qcow2/boot/grub/grub.cfg > /dev/null
set default=0
set timeout=$GRUB_TIMEOUT

menuentry "StarshipOS" {
    set root=(hd0,1)
    linux /boot/bzImage root=/dev/sda1 rw console=ttyS0
    init=/init
}
EOF

sync

# Step 8: Cleanup
echo "Unmounting and cleaning up..."
sudo umount /mnt/qcow2
sudo qemu-nbd --disconnect /dev/nbd0
sudo rm -rf /mnt/qcow2
echo "QCOW2 image created successfully!"

# Step 9: Launch QEMU
echo "Launching QEMU with $QCOW2_IMAGE..."
sudo chown "$(whoami)":"$(whoami)" "$QCOW2_IMAGE"
qemu-system-x86_64 -drive file="$QCOW2_IMAGE",format=qcow2 -m 4096 -cpu qemu64 -smp 2
