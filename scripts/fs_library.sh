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
function unmount_rootfs() {
  # Debugging step to check if all required commands are available
  log "DEBUG: Checking available commands..."
  for cmd in mountpoint umount awk losetup sync; do
    if ! command -v "$cmd" > /dev/null; then
      log_error "$cmd command not found. Ensure it is installed and available in PATH."
      return 127
    fi
  done

  # Flush filesystem buffers to ensure all I/O operations are complete
  log "Flushing filesystem buffers with sync..."
  sync
  if [ $? -eq 0 ]; then
    log "Filesystem buffers successfully flushed."
  else
    log_error "Failed to flush filesystem buffers. Proceeding with caution."
  fi

  # Check if /mnt/rootfs is currently mounted
  if mountpoint -q "/mnt/rootfs"; then
    log "Unmounting root filesystem from /mnt/rootfs..."
    sudo umount -lf "/mnt/rootfs" # Lazy force unmount
    if [ $? -eq 0 ]; then
      log "Successfully unmounted /mnt/rootfs."
    else
      log_error "Failed to unmount /mnt/rootfs."
      return 1
    fi
  else
    log "No filesystem is mounted at /mnt/rootfs."
  fi

  # Pause briefly to ensure the unmount process finishes releasing resources
  log "Waiting briefly for unmount operations to fully release resources..."
  sleep 1

  # Attempt to find and detach loop devices associated with rootfs.ext4
  log "Checking for loop devices associated with rootfs.ext4..."
  LOOPS=$(sudo losetup -j ./rootfs.ext4 | awk -F: '{print $1}')
  if [ -z "$LOOPS" ]; then
    log "No loop device associated with rootfs.ext4 to detach. Likely already released."
  else
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
  fi

  # Final confirmation of successful cleanup
  log "Unmount and cleanup of root filesystem and loop device completed successfully."
}

# Function to unmount a root filesystem
function unmount_rootfs() {
  # Debugging step to check if all required commands are available
  log "DEBUG: Checking available commands..."
  for cmd in mountpoint umount awk losetup sync; do
    if ! command -v "$cmd" > /dev/null; then
      log_error "$cmd command not found. Ensure it is installed and available in PATH."
      return 127
    fi
  done

  # Flush filesystem buffers to ensure all I/O operations are complete
  log "Flushing filesystem buffers with sync..."
  sync
  if [ $? -eq 0 ]; then
    log "Filesystem buffers successfully flushed."
  else
    log_error "Failed to flush filesystem buffers. Proceeding with caution."
  fi

  # Check if /mnt/rootfs is currently mounted
  if mountpoint -q "/mnt/rootfs"; then
    log "Unmounting root filesystem from /mnt/rootfs..."
    sudo umount -lf "/mnt/rootfs" # Lazy force unmount
    if [ $? -eq 0 ]; then
      log "Successfully unmounted /mnt/rootfs."
    else
      log_error "Failed to unmount /mnt/rootfs."
      return 1
    fi
  else
    log "No filesystem is mounted at /mnt/rootfs."
  fi

  # Pause briefly to ensure the unmount process finishes releasing resources
  log "Waiting briefly for unmount operations to fully release resources..."
  sleep 1

  # Detach the loop device explicitly using exported variable
  if [ -n "$LOOP_DEVICE" ]; then
    log "Detaching loop device $LOOP_DEVICE..."
    sudo losetup -d "$LOOP_DEVICE"
    if [ $? -eq 0 ]; then
      log "Successfully detached loop device $LOOP_DEVICE."
    else
      log_error "Failed to detach loop device $LOOP_DEVICE."
      return 1
    fi
  else
    log "LOOP_DEVICE is not set. No loop device to detach."
    return 1
  fi

  # Final confirmation of successful cleanup
  sudo rm -rfv /mnt/rootfs
  log "Unmount and cleanup of root filesystem and loop device completed successfully."
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
