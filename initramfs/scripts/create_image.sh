#!/bin/bash
# shellcheck disable=SC2164
if [ ! -d build ]; then

# Define the image path
IMAGE_PATH="target/initramfs_img"
STARSHIP_ROOT="$1"
echo "Create image directory if it does not exist."
if [ ! -d "$IMAGE_PATH" ]; then
  echo "initramfs directory created."
  mkdir -p "$IMAGE_PATH"
else
  echo "initramfs directory exists."
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
mkdir -p bin
mkdir -p sbin
mkdir -p usr
cp -r "$STARSHIP_ROOT"/busybox/build/bin/* ./bin/
cp -r "$STARSHIP_ROOT"/busybox/build/sbin/* ./sbin/
cp -r "$STARSHIP_ROOT"/busybox/build/usr/* ./usr/

# Create symlinks for all busybox provided commands
#echo "Creating symlinks for busybox utilities..."
#for cmd in $(./bin/busybox --list); do
#    ln -sf busybox "$cmd"
#done
#echo "All symlinks created for Busybox utilities."

# Populate the ramdisk by copying files to the initramfs directory before writing the image.
# shellcheck disable=SC2162
echo "Creating initramfs_img.gz..."

mkdir -p /home/rajames/PROJECTS/StarshipOS/initramfs/build

# you may add other content here.

# you may add other content before the line below.
find . -print0 | cpio --null -ov --format=newc | gzip -9 > ./initramfs_img.gz
mv ./initramfs_img.gz /home/rajames/PROJECTS/StarshipOS/initramfs/build

# Return to the original directory
cd - || exit 1
rm -rf initramfs
fi
echo "Initramfs creation complete."
