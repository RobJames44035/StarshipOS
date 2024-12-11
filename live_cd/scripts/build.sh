#!/bin/bash
# shellcheck disable=SC2162

set -e  # Exit immediately if a command exits with a non-zero status
set -u  # Treat unset variables as an error

ISO_NAME="starship-os.iso"
HOME="/home/rajames/PROJECTS/StarshipOS"

# Logging function
function log() {
    echo "[$(date +'%Y-%m-%d %H:%M:%S')] $1"
}
if [ ! -d build ]; then

  log "Starting ISO creation process..."
  mkdir -p "${HOME}/live_cd/build/iso-assembly/boot/grub"

  # Copy the live_cd directory to the iso-assembly directory
  log "Copying initramfs to iso-assembly... /boot"
  cp -r "$HOME/initramfs/build/initramfs.img" "$HOME/live_cd/build/iso-assembly/boot"

  # Copy the kernel.
  log "Copying kernel to iso-assembly... /boot"
  cp -r "$HOME/starship/build/init_ram_fs/boot/starship" "$HOME/live_cd/build/iso-assembly/boot"

  # Copy kernel modules
  log "Copying kernel modules."
  cp -r "$HOME/starship/build/init_ram_fs/lib" "$HOME/live_cd/build/iso-assembly"

  # Copy grub.cfg
  log "Copying grub.cfg to /boot/grub/grub.cfg"
  cp "$HOME/grub/build/init_ram_fs/boot/grub/grub.cfg" "$HOME/live_cd/build/iso-assembly/boot/grub"

  log "Copying busybox /"
  log "/bin"
  cp -r "$HOME/busybox/build/init_ram_fs/bin" "$HOME/live_cd/build/iso-assembly"
  log "/sbin"
  cp -r "$HOME/busybox/build/init_ram_fs/sbin" "$HOME/live_cd/build/iso-assembly"
  log "/usr"
  cp -r "$HOME/busybox/build/init_ram_fs/usr" "$HOME/live_cd/build/iso-assembly"
  log "/linuxrc"
  cp -r "$HOME/busybox/build/init_ram_fs/linuxrc" "$HOME/live_cd/build/iso-assembly"

  sudo grub-mkrescue -boot_image "grub" -o "$HOME/live_cd/build/$ISO_NAME" "$HOME/live_cd/build/iso-assembly" 2>&1 | tee iso_creation.log || { log "ISO creation failed"; exit 1; }

  log "Bootable ISO created successfully in $HOME/live_cd/build/$ISO_NAME."
else
  log "Nothing to do."
fi
