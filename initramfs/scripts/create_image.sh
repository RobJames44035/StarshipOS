#!/bin/bash

# Define the image path
IMAGE_PATH="/home/rajames/PROJECTS/StarshipOS/initramfs/build/initramfs"

echo "Create directory if it does not exist."
initramfs_dir=$(dirname "$IMAGE_PATH")
if [ ! -d "$initramfs_dir" ]; then
    echo "Directory $initramfs_dir does not exist. Creating..."
    mkdir -p "$initramfs_dir"
fi

echo "Execute the dd command."
if [ -d "$initramfs_dir" ]; then
    dd if=/dev/zero of=$IMAGE_PATH bs=1M count=10
    # shellcheck disable=SC2181
    if [ $? -eq 0 ]; then
        echo "dd command executed successfully"
    else
        echo "dd command failed"
    fi
else
    echo "Failed to create directory $initramfs_dir"
    exit 1
fi
