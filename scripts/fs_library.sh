#!/bin/bash

#
# StarshipOS Copyright (c) 2025. R.A.James
#
# Licensed under GPL2, GPL3 and Apache 2
#

# Function to log informational messages
function log() {
  local message=$1
  local timestamp=$(date +"%Y-%m-%d %H:%M:%S")
  echo "[$timestamp] [INFO] $message"
}

# Function to log error messages
function log_error() {
  local message=$1
  local timestamp=$(date +"%Y-%m-%d %H:%M:%S")
  echo "[$timestamp] [ERROR] $message" >&2
}

# Function to mount a root filesystem
function mount_rootfs() {

# Define variables
disk_image="../rootfs.ext4"
mount_dir="/mnt/rootfs"

# Ensure the disk image exists
if [ ! -f "$disk_image" ]; then
  log_error "Disk image '$disk_image' does not exist. Exiting."
  exit 1
fi

# Ensure the mount directory exists
sudo mkdir -p "$mount_dir"

# Log the operation being performed
log "Locating free loop device for disk image at '$disk_image'."

# Assign a loop device for the disk image
LOOPDEV=$(sudo losetup -fP --show "$disk_image" 2>/dev/null)
if [ $? -ne 0 ] || [ -z "$LOOPDEV" ]; then
  log_error "Failed to set up loop device for '$disk_image'."
  exit 1
fi
log "Loop device $LOOPDEV assigned to '$disk_image'."

# Attempt to mount the disk image
log "Mounting disk image '$disk_image' at '$mount_dir'."
sudo mount -o loop "$disk_image" "$mount_dir" 2>/dev/null
if [ $? -ne 0 ]; then
  log_error "Failed to mount '$disk_image' to '$mount_dir'."
  log "Cleaning up loop device $LOOPDEV."
  sudo losetup -d "$LOOPDEV"
  exit 1
fi

# Success message
log "Disk image '$disk_image' mounted successfully at '$mount_dir' using loop device $LOOPDEV. Press [ENTER] to continue."
}


# Function to unmount a root filesystem
function unmount_rootfs() {
  if mountpoint -q "/mnt/rootfs"; then
    log "Unmounting root filesystem from /mnt/rootfs..."
    sudo umount -lf "/mnt/rootfs"
    if [ $? -eq 0 ]; then
      log "Successfully unmounted /mnt/rootfs."
    else
      log_error "Failed to unmount /mnt/rootfs."
      return 1
    fi
  else
    log "No filesystem is mounted at /mnt/rootfs."
  fi

 LOOPS=$(sudo losetup -j ./rootfs.ext4 | awk -F: '{print $1}')
    if [ -n "$LOOPS" ]; then
      for loop in $LOOPS; do
        log "Detaching loop device $loop..."
        sudo losetup -d "$loop"
        if [ $? -eq 0 ]; then
          log "Successfully detached loop device $loop."
        else
          log_error "Failed to detach loop device $loop."
          return 1
        fi
      done
    else
      log "No loop device associated with rootfs.ext4 to detach."
    fi

  log "Unmount and cleanup of root filesystem and loop device complete."
}
trap unmount_rootfs SIGINT EXIT

# Helper function to pause script execution for debugging or inspection
# Accepts an optional message
function pause() {
  local message=$1
  if [ -z "$message" ]; then
    message="Press any key to continue..."
  fi
  echo "${message}"
  # shellcheck disable=SC2162
  read -p "x"
}
