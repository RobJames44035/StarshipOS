#!/bin/bash

JAVA_VERSION="23.0.2"
FELIX_VERSION="7.0.5"
ACTIVEMQ_VERSION="6.1.5"

#
# StarshipOS Copyright (c) 2025. R. A. James
#
function pause() {
  echo "Paused... [ENTER] to continue ^C to quit."
  read -p "x"
}

#
# The `mount_rootfs` function locates a free loop device, mounts a root filesystem image
# to a specific directory (`/mnt/rootfs`), and then outputs the loop device assigned to the image.
# This function requires `sudo` permissions to execute.
#
function mount_rootfs() {
  echo "Locating free loop device."
  sudo mkdir -p "/mnt/rootfs"
  LOOPDEV=$(sudo losetup -fP --show "../../buildroot/buildroot/output/images/rootfs.ext4")
  sudo mount -o loop "../../buildroot/buildroot/output/images/rootfs.ext4" "/mnt/rootfs"
  echo "$LOOPDEV @ /mnt/rootfs"
}

#
# The `make_dirs` function is designed to create necessary directories inside the mounted
# root filesystem. Note that the actual directory creation commands are currently commented out.
#
function make_dirs() {
  echo "Creating directories."
}

#
# The `copy_files` function extracts and copies specific files (e.g., Apache Artemis and Felix Framework)
# into the mounted root filesystem under the `/opt` directory. Temporary files are also left in
# the working directory and later cleaned in another function.
#
function copy_files() {
  echo "Copy files..."

  echo "#"
  echo "# OpenJDK $JAVA_VERSION"
  echo "#"
  sudo wget "https://download.java.net/java/GA/jdk${JAVA_VERSION}/6da2a6609d6e406f85c491fcb119101b/7/GPL/openjdk-${JAVA_VERSION}_linux-x64_bin.tar.gz" -O "repo/openjdk-${JAVA_VERSION}_linux-x64_bin.tar.gz"
  sudo tar xvf "repo/openjdk-${JAVA_VERSION}_linux-x64"
  sudo cp -rpv "./openjdk-${JAVA_VERSION}" "/mnt/rootfs/"
  sudo mv "/mnt/rootfs/openjdk-${JAVA_VERSION}/" "/mnt/rootfs/jdk"

  echo "#"
  echo "# Apache Felix v${FELIX_VERSION}"
  echo "#"
  sudo wget "https://www.apache.org/dyn/closer.lua/felix/org.apache.felix.main.distribution-${FELIX_VERSION}.zip?action=download" -O "repo/org.apache.felix.main.distribution-${FELIX_VERSION}.zip"
  sudo unzip "repo/org.apache.felix.main.distribution-${FELIX_VERSION}.zip"
  sudo cp -rpv "./felix-framework-${FELIX_VERSION}" "/mnt/rootfs/opt"

  echo "#"
  echo "# Apache ActiveMQ v${ACTIVEMQ_VERSION}"
  echo "#"
  sudo wget "https://www.apache.org/dyn/closer.cgi?filename=/activemq/${ACTIVEMQ_VERSION}/apache-activemq-${ACTIVEMQ_VERSION}-bin.tar.gz&action=download" -O "repo/apache-activemq-${ACTIVEMQ_VERSION}-bin.tar.gz"
  sudo tar xvf "repo/apache-activemq-${ACTIVEMQ_VERSION}-bin.tar.gz"
  sudo cp -rpv "./apache-activemq-${ACTIVEMQ_VERSION}" "/mnt/rootfs/opt"
  sudo mv "/mnt/rootfs/opt/apache-activemq-${ACTIVEMQ_VERSION}/" "/mnt/rootfs/opt/activemq"
  sudo chmod +x "/mnt/rootfs/opt/activemq/bin/activemq"
}

#
# The `unmount_rootfs` function unmounts the root filesystem and ensures all data is properly
# synced before unmounting to avoid corruption.
#
function unmount_rootfs() {
  echo "Cleanup"
  sudo sync
  sudo umount "/mnt/rootfs/"
}

#
# The `cleanup_litter` function removes temporary files and directories created during the copying process,
# ensuring a clean working directory.
#
function cleanup_litter() {
  echo "Cleaning up."
  sudo rm -rf "./apache-activemq-6.1.5"
  sudo rm -rf "./felix-framework-7.0.5"

  sudo rm "repo/apache-activemq-6.1.5-bin.tar.gz"
  sudo rm "repo/org.apache.felix.main.distribution-7.0.5.zip"

  sudo sync
}

#
# The `main` function orchestrates the entire process by invoking the other functions in the
# required sequence: mounting the filesystem, creating directories, copying files, cleaning up,
# and unmounting.
#
function main() {
  mount_rootfs
  make_dirs
  copy_files
pause
  cleanup_litter
  unmount_rootfs
}

main
