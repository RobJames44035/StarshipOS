#!/bin/bash

#
# StarshipOS Copyright (c) 2025. R. A. James
#

JAVA_VERSION="23.0.2"
FELIX_VERSION="7.0.5"
ACTIVEMQ_VERSION="6.1.5"

JAVA_DOWNLOAD="https://download.java.net/java/GA/jdk${JAVA_VERSION}/6da2a6609d6e406f85c491fcb119101b/7/GPL/openjdk-${JAVA_VERSION}_linux-x64_bin.tar.gz"
FELIX_DOWNLOAD="https://www.apache.org/dyn/closer.lua/felix/org.apache.felix.main.distribution-${FELIX_VERSION}.zip?action=download"
ACTIVEMQ_DOWNLOAD="https://www.apache.org/dyn/closer.cgi?filename=/activemq/${ACTIVEMQ_VERSION}/apache-activemq-${ACTIVEMQ_VERSION}-bin.tar.gz&action=download"
GRAAL_DOWNLOAD="https://download.oracle.com/graalvm/23/latest/graalvm-jdk-23_linux-x64_bin.tar.gz"
#
# The `pause` function displays a message prompting the user to either press the ENTER key to continue
# or use ^C to quit the process. It halts the script execution until the user provides input.
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
  echo "Creating directories." # if needed
  mkdir -p "repo"
  sudo mkdir -p "/mbt/rootfs/java"
  sudo mkdir -p "/mbt/rootfs/graal"
}

#
# The `jdk` function downloads the specified OpenJDK version for Linux, extracts it, 
# copies it to the mounted root filesystem, and then renames the directory to `jdk`. 
# This function ensures the correct Java Development Kit is installed in the root filesystem.
#
function jdk() {
  echo "#"
  echo "# OpenJDK $JAVA_VERSION"
  echo "#"

  # Check if the file already exists
  if [ ! -f "repo/openjdk-${JAVA_VERSION}_linux-x64_bin.tar.gz" ]; then
    echo "Downloading OpenJDK ${JAVA_VERSION}..."
    sudo wget "${JAVA_DOWNLOAD}" -O "repo/openjdk-${JAVA_VERSION}_linux-x64_bin.tar.gz"
  else
    echo "repo/openjdk-${JAVA_VERSION}_linux-x64_bin.tar.gz already exists. Skipping download."
  fi

  # Extract the tarball
  sudo tar xvf "repo/openjdk-${JAVA_VERSION}_linux-x64_bin.tar.gz"
  sudo mv -fv "./jdk-${JAVA_VERSION}" "/mnt/rootfs/java"
}

#
# The `graal` function downloads the specified GraalVM version for Linux, extracts it,
# copies it to the mounted root filesystem, and then renames the directory to `graal`.
# This function ensures the correct Java Development Kit is installed in the root filesystem.
#
function graal() {
  echo "#"
  echo "# GraalVM 23"
  echo "#"
  # Check if the file already exists
  if [ ! -f "repo/graalvm-jdk-23_linux-x64_bin.tar.gz" ]; then
    echo "Downloading GraalVM 23..."
    sudo wget "${GRAAL_DOWNLOAD}" -O "repo/graalvm-jdk-23_linux-x64_bin.tar.gz"
  else
    echo "repo/graalvm-jdk-23_linux-x64_bin.tar.gz already exists. Skipping download."
  fi
  # Extract the tarball
  sudo tar xvf "repo/graalvm-jdk-23_linux-x64_bin.tar.gz"
  sudo mv -fv "./graalvm-jdk-23.0.2+7.1/" "/mnt/rootfs/graalvm"
}

#
# The `felix` function is responsible for downloading the specified version of Apache Felix framework,
# extracting it, and copying the extracted files into the `/mnt/rootfs/opt` directory within the
# mounted filesystem. This setup places the framework in the correct location for further use within
# the system environment.
#
function felix() {
  echo "#"
  echo "# Apache Felix v${FELIX_VERSION}"
  echo "#"

  # Check if the file already exists
  if [ ! -f "repo/org.apache.felix.main.distribution-${FELIX_VERSION}.zip" ]; then
    echo "Downloading Apache Felix v${FELIX_VERSION}..."
    sudo wget "${FELIX_DOWNLOAD}" -O "repo/org.apache.felix.main.distribution-${FELIX_VERSION}.zip"
  else
    echo "repo/org.apache.felix.main.distribution-${FELIX_VERSION}.zip already exists. Skipping download."
  fi

  sudo unzip -o "repo/org.apache.felix.main.distribution-${FELIX_VERSION}.zip"
  sudo mv -fv "./felix-framework-${FELIX_VERSION}" "/mnt/rootfs/opt/felix"
}

#
# The `activemq` function downloads the specified Apache ActiveMQ version for Linux,
# extracts it, copies it to the `/mnt/rootfs/opt` directory in the mounted filesystem,
# renames the directory to `activemq`, and makes the `activemq` executable available.
# This ensures the application is properly set up for further use within the environment.
#
function activemq() {
  echo "#"
  echo "# Apache ActiveMQ v${ACTIVEMQ_VERSION}"
  echo "#"

  # Check if the file already exists
  if [ ! -f "repo/apache-activemq-${ACTIVEMQ_VERSION}-bin.tar.gz" ]; then
    echo "Downloading Apache ActiveMQ v${ACTIVEMQ_VERSION}..."
    sudo wget "${ACTIVEMQ_DOWNLOAD}" -O "repo/apache-activemq-${ACTIVEMQ_VERSION}-bin.tar.gz"
  else
    echo "repo/apache-activemq-${ACTIVEMQ_VERSION}-bin.tar.gz already exists. Skipping download."
  fi

  # Extract the tarball
  sudo tar xvf "repo/apache-activemq-${ACTIVEMQ_VERSION}-bin.tar.gz"
  sudo mv -fv "./apache-activemq-${ACTIVEMQ_VERSION}" "/mnt/rootfs/opt/felix"
}

#
# The `copy_files` function extracts and copies specific files (e.g., Apache Artemis and Felix Framework)
# into the mounted root filesystem under the `/opt` directory. Temporary files are also left in
# the working directory and later cleaned in another function.
#
function copy_files() {
  echo "Copy files..."
  jdk
#  graal
#  activemq
#  felix
}

#
# The `unmount_rootfs` function unmounts the root filesystem and ensures all data is properly
# synced before unmounting to avoid corruption.
#
function unmount_rootfs() {
  echo "unmount"
  sudo sync
  sudo umount "/mnt/rootfs/"
}

#
# The `cleanup_litter` function removes temporary files and directories created during the copying process,
# ensuring a clean working directory.
#
function cleanup_litter() {
  echo "Cleaning up."
  sudo rm -rf "./jdk-23.0.2"
  sudo rm -rf "./apache-activemq-6.1.5"
  sudo rm -rf "./felix-framework-7.0.5"
  sudo rm -rf "./graalvm-jdk-23_linux-x64_bin.tar.gz"
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
