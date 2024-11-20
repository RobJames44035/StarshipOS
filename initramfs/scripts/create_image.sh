#!/bin/bash

# Define the image path
IMAGE_PATH="initramfs"

echo "Create image directory if it does not exist."
if [ ! -d "$IMAGE_PATH" ]; then
  echo "initramfs directory created."
  mkdir -p "$IMAGE_PATH"
else
  echo "initramfs directory exists."
  rm -rfv "$IMAGE_PATH"
  mkdir -p "$IMAGE_PATH"
fi

# Navigate to the initramfs directory
cd "$IMAGE_PATH" || exit 1

# Create the dev directory
mkdir -p dev

# Create essential device nodes
echo "Creating essential device nodes..."
sudo mknod -m 600 dev/console c 5 1
sudo mknod -m 666 dev/null c 1 3

# Create the linuxrc script
echo "Creating linuxrc script..."
cat << 'EOF' > linuxrc
#!/bin/sh

# Mount essential filesystem directories
echo "Booting StarshipOS"
mount -t proc none /proc
mount -t sysfs none /sys
mount -t tmpfs none /tmp

# Insert additional initialization logic here if needed

# Switch to the real root filesystem
exec /bin/sh
EOF
chmod +x linuxrc

# Copy the busybox binary to the initramfs directory
echo "Copying busybox to initramfs..."
cp /home/rajames/PROJECTS/StarshipOS/busybox/build/bin/busybox/bin .

# Create symlinks for all busybox provided commands
echo "Creating symlinks for busybox utilities..."
for cmd in $(./busybox --list); do
    ln -sf busybox "$cmd"
done
echo "All symlinks created for Busybox utilities."

# Populate the ramdisk by copying files to the initramfs directory before writing the image.
read -p "initramfs ready to write. Press any key..."

echo "Creating initramfs.gz..."
find . | cpio -o --format=newc | gzip > ../initramfs.gz

# Return to the original directory
cd - || exit 1

echo "Initramfs creation complete."
