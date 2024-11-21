#!/bin/bash
# shellcheck disable=SC2164

BUILD_DIR="build/boot"
OUTPUT_FILE="$BUILD_DIR/grub.cfg"

if [ ! -d "$BUILD_DIR" ]; then
  mkdir -p "$BUILD_DIR"

  cat <<EOF > "$OUTPUT_FILE"
# GRUB Configuration file

# Set the default boot entry to the first entry in the menu
set default=0

# Set the timeout before the default boot entry is selected
set timeout=15

menuentry "Starship" {
  set root=(cd)
  linux /boot/starship root=/dev/ram0 rw
  initrd /boot/initramfs.img
}
EOF
fi
