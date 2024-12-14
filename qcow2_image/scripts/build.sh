#!/bin/bash
# shellcheck disable=SC2164

set -e  # Exit immediately if a command exits with a non-zero status
set -u  # Treat unset variables as an error

HOME="/home/rajames/PROJECTS/StarshipOS"
THE_COW="$HOME/qcow2_image"
FINAL_BUILD_DIR="$THE_COW/build"
TARGET_DIR="$THE_COW/target"
ROOT_MOUNT_POINT="$TARGET_DIR/mnt"
BOOT_MOUNT_POINT="$TARGET_DIR/boot"

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

  log "Creating disk image."
  mkdir -p "$FINAL_BUILD_DIR"
  mkdir -p "$TARGET_DIR"
  mkdir -p "$ROOT_MOUNT_POINT"

  log "Create a 2TB qcow2 image."
  qemu-img create -f qcow2 "$FINAL_BUILD_DIR/$HDD_VIRT_NAME" 512G

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
  sudo mount "$ROOT_PART" "$ROOT_MOUNT_POINT"

  log "Creating $BOOT_MOUNT_POINT mount point and mounting."
  sudo mkdir -p "$BOOT_MOUNT_POINT"
  sudo mount "$BOOT_PART" "$BOOT_MOUNT_POINT"

  #######################################
  # Add content to boot partition /boot #
  #######################################
  echo "#######################################"
  echo "# Add content to boot partition /boot #"
  echo "#######################################"

  log "Copying kernel."
  sudo cp "../starship/build/init_ram_fs/boot/starship" "$BOOT_MOUNT_POINT/starship"

  log "Copy kernel modules."
  sudo cp -rv "../starship/build/init_ram_fs/lib" "$BOOT_MOUNT_POINT"

  log "Copying initramfs."
  sudo cp "../initramfs/build/initramfs.img" "$BOOT_MOUNT_POINT/initramfs"

  sudo mkdir -p "$BOOT_MOUNT_POINT/grub"
  log "Copying grub.cfg"
  sudo cp "../grub/build/init_ram_fs/boot/grub/grub.cfg" "$BOOT_MOUNT_POINT/grub/grub.cfg"

  ########################################
  #    Add content to root partition /   #
  ########################################
  echo "########################################"
  echo "#    Add content to root partition /   #"
  echo "########################################"

  # BusyBox
  log "Copying BusyBox."
  sudo cp -rv "../busybox/build/init_ram_fs/bin" "$ROOT_MOUNT_POINT"
  sudo cp -rv "../busybox/build/init_ram_fs/sbin" "$ROOT_MOUNT_POINT"
  sudo cp -rv "../busybox/build/init_ram_fs/usr" "$ROOT_MOUNT_POINT"
  # Java JDK
  log "Copying JDK23."
  sudo cp -rv "../java/build/jdk/jdk/bin" "$ROOT_MOUNT_POINT"
  sudo cp -rv "../java/build/jdk/jdk/conf" "$ROOT_MOUNT_POINT"
  sudo cp -rv "../java/build/jdk/jdk/include" "$ROOT_MOUNT_POINT"
  sudo cp -rv "../java/build/jdk/jdk/lib" "$ROOT_MOUNT_POINT"
  sudo cp -rv "../java/build/jdk/jdk/man" "$ROOT_MOUNT_POINT"
  sudo cp -rv "../java/build/jdk/jdk/modules" "$ROOT_MOUNT_POINT"
###########################################################
# You may add other content here as needed.
###########################################################



#  log "Adding init script."
#  sudo bash -c "cat << 'EOF' > \"$BOOT_MOUNT_POINT/linuxrc\"
##!/bin/sh
#
#echo 'Starship getting ready for Warp.'
## Mount the proc and sys filesystems
#mount -t proc proc /proc
#mount -t sysfs sys /sys
#
## Create device nodes
#mknod -m 622 /dev/console c 5 1
#mknod -m 666 /dev/null c 1 3
#
### export JAVA_HOME=/usr/lib/jvm/jdk
### export PATH=\$JAVA_HOME/bin:\$PATH
##
## Run BusyBox init
#echo 'Engines started!'
#exec /bin/busybox init
#EOF
#"
#
#  sudo chmod +x "$BOOT_MOUNT_POINT/linuxrc"
#
#  # Install GRUB2 bootloader
#  log "Install GRUB2 bootloader."
#  sudo grub-install --target=i386-pc --boot-directory="$BOOT_MOUNT_POINT" --recheck "$NDB0"
#
#  # Disconnect the disk
#  log "Unmount and disconnect the disk."
#  sudo umount "$BOOT_MOUNT_POINT"
#  sudo umount "$ROOT_MOUNT_POINT"
#  sudo qemu-nbd --disconnect "$NDB0"
#
#  log "starship-os.qcow2 has been created successfully."
else
    log "Nothing to do.."
fi
