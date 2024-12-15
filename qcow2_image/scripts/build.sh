#!/bin/bash
# shellcheck disable=SC2164

set -e  # Exit immediately if a command exits with a non-zero status
set -u  # Treat unset variables as an error

HOME="/home/rajames/PROJECTS/StarshipOS"
THE_COW="$HOME/qcow2_image"
FINAL_BUILD_DIR="$THE_COW/build"

TARGET_DIR="$THE_COW/target"

ROOT_MOUNT_POINT="$FINAL_BUILD_DIR/mnt"
BOOT_MOUNT_POINT="$FINAL_BUILD_DIR/boot"

HDD_VIRT_NAME="starship-os.qcow2"
NDB0="/dev/nbd0"
# shellcheck disable=SC2034
BIOS_PART="/dev/nbd0p1" # BIOS Boot partition (1MB)
BOOT_PART="/dev/nbd0p2" # boot partition
ROOT_PART="/dev/nbd0p3" # root partition

function log() {
    echo "[$(date +'%Y-%m-%d %H:%M:%S')] $1"
#    read -p "breakpoint"
}

if [ ! -d build ]; then
  mkdir -p "$FINAL_BUILD_DIR"
  qemu-img create -f qcow2 "$FINAL_BUILD_DIR/$HDD_VIRT_NAME" 4G

# Connect the qcow2 image as a block device
  log "Connect the qcow2 image as a block device."
  sudo modprobe nbd max_part=16
  sudo qemu-nbd --connect="$NDB0" "$FINAL_BUILD_DIR/$HDD_VIRT_NAME"

  # Create the partition table
  log "Create the partition table."
  sudo parted -s "$NDB0" -- mklabel gpt

  # Create BIOS Boot partition (1MB)
  log "Create BIOS Boot partition (1MB)."
  sudo parted -s "$NDB0" -- mkpart primary 1MiB 2MiB
  sudo parted -s "$NDB0" -- set 1 bios_grub on

  # Create /boot partition (1GB)
  log "Create /boot partition (1GB)."
  sudo parted -s "$NDB0" -- mkpart primary ext4 2MiB 1026MiB

  # Create root (/) partition with remaining space
  log "Create root (/) partition with remaining space."
  sudo parted -s "$NDB0" -- mkpart primary ext4 1026MiB 100%

  # Format the /boot partition
  log "Format the /boot partition (ext4)."
  sudo mkfs.ext4 "$BOOT_PART"

  # Format the root (/) partition
  log "Format the root (/) partition (ext4)."
  sudo mkfs.ext4 "$ROOT_PART"

  log "Create $ROOT_MOUNT_POINT mount point and mounting."
  mkdir -p "$ROOT_MOUNT_POINT"
  sudo mount "$ROOT_PART" "$ROOT_MOUNT_POINT"

  log "Creating $BOOT_MOUNT_POINT mount point and mounting."
  mkdir -p "$BOOT_MOUNT_POINT"
  sudo mount "$BOOT_PART" "$BOOT_MOUNT_POINT"

  #######################################
  # Add content to boot partition /boot #
  #######################################
  echo "#######################################"
  echo "# Add content to boot partition /boot #"
  echo "#######################################"

  log "Copying kernel."
  sudo cp -p "./target/kernel/build/init_ram_fs/boot/starship" "build/boot/starship"

  log "Copy kernel modules."
  sudo cp -rp "./target/kernel/build/init_ram_fs/lib" "build/boot"

  log "Copying initramfs."
  sudo cp -p "target/ramdisks/build/initramfs.hd0.gz" "build/boot"

  sudo mkdir -p "$BOOT_MOUNT_POINT/grub"
  log "Copying grub.cfg"
  sudo cp -p "./target/grub/hd0/init_ram_fs/boot/grub.cfg" "build/boot/grub/grub.cfg"

  # Install GRUB bootloader
  log "Installing GRUB bootloader."
  sudo grub-install --target=i386-pc --boot-directory="$BOOT_MOUNT_POINT" "$NDB0"

#  tree "build/boot"
  ########################################
  #    Add content to root partition /   #
  ########################################
  echo "########################################"
  echo "#    Add content to root partition /   #"
  echo "########################################"
  sudo mkdir -p build/mnt/bin \
         build/mnt/dev \
         build/mnt/etc \
         build/mnt/home \
         build/mnt/lib \
         build/mnt/lib64 \
         build/mnt/mnt \
         build/mnt/opt \
         build/mnt/proc \
         build/mnt/root \
         build/mnt/sbin \
         build/mnt/sys \
         build/mnt/tmp \
         build/mnt/usr/bin \
         build/mnt/usr/lib \
         build/mnt/usr/lib64 \
         build/mnt/usr/sbin \
         build/mnt/var/log \
         build/mnt/var/tmp \
         build/mnt/var/run

  # BusyBox
  log "Copying BusyBox."
  sudo cp -rp "./target/busybox/build/init_ram_fs/bin" "build/mnt"
  sudo cp -rp "./target/busybox/build/init_ram_fs/sbin" "build/mnt"

  # Java JDK
  log "Copying JDK23."
  sudo mkdir -p "build/mnt/java/jdk"
  sudo cp -rp "./target/java/build/jdk" "build/mnt/java/jdk"
###########################################################
# You may add other content below as needed.
###########################################################

#tree "build/mnt"
# Cleanup at the end

  sudo rm -rf "$BOOT_MOUNT_POINT/*"
  sudo umount --lazy "$BOOT_MOUNT_POINT"
  sudo rm -rf "$ROOT_MOUNT_POINT/*"
  sudo umount --lazy "$ROOT_MOUNT_POINT"
  sudo qemu-nbd --disconnect /dev/nbd0
else
    log "Nothing to do.."
fi
