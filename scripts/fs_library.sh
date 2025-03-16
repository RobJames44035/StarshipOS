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
# Accepts the disk image path as an argument
function mount_rootfs() {
  local disk_image=$1
  if [ -z "$disk_image" ]; then
    log_error "Disk image path not provided to mount_rootfs."
    return 1
  fi

  log "Locating free loop device for disk image $disk_image."
  sudo mkdir -p "/mnt/rootfs" # Ensure mount directory exists
  LOOPDEV=$(sudo losetup -fP --show "$disk_image")
  if [ $? -ne 0 ]; then
    log_error "Failed to set up loop device for $disk_image."
    return 1
  fi

  sudo mount -o loop "$disk_image" "/mnt/rootfs"
  if [ $? -ne 0 ]; then
    log_error "Failed to mount $disk_image to /mnt/rootfs."
    sudo losetup -d "$LOOPDEV"
    return 1
  fi

  log "Disk image $disk_image mounted at /mnt/rootfs using loop device $LOOPDEV."
}

# Function to unmount a root filesystem
function unmount_rootfs() {
  if mountpoint -q "/mnt/rootfs"; then
    log "Unmounting root filesystem from /mnt/rootfs..."
    sudo umount "/mnt/rootfs"
    if [ $? -eq 0 ]; then
      log "Successfully unmounted /mnt/rootfs."
    else
      log_error "Failed to unmount /mnt/rootfs."
      return 1
    fi
  else
    log "No filesystem is mounted at /mnt/rootfs."
  fi

  if [ -n "$LOOPDEV" ]; then
    log "Detaching loop device $LOOPDEV..."
    sudo losetup -d "$LOOPDEV"
    if [ $? -eq 0 ]; then
      log "Successfully detached loop device $LOOPDEV."
    else
      log_error "Failed to detach loop device $LOOPDEV."
      return 1
    fi
  else
    log "No loop device to detach."
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
  read -p "x"
}
