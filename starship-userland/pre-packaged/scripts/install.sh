#!/bin/bash

#
# StarshipOS Copyright (c) 2025. R.A.James
#
# Licensed under GPL2, GPL3 and Apache 2
#

# Source the library file
source ../../scripts/fs_library.sh
#
# Set versions and download URLs
#
JAVA_VERSION="23.0.2"
FELIX_VERSION="7.0.5"
ACTIVEMQ_VERSION="6.1.5"

JAVA_DOWNLOAD="https://download.java.net/java/GA/jdk${JAVA_VERSION}/6da2a6609d6e406f85c491fcb119101b/7/GPL/openjdk-${JAVA_VERSION}_linux-x64_bin.tar.gz"
FELIX_DOWNLOAD="https://www.apache.org/dyn/closer.lua/felix/org.apache.felix.main.distribution-${FELIX_VERSION}.zip?action=download"
ACTIVEMQ_DOWNLOAD="https://www.apache.org/dyn/closer.cgi?filename=/activemq/${ACTIVEMQ_VERSION}/apache-activemq-${ACTIVEMQ_VERSION}-bin.tar.gz&action=download"
GRAAL_DOWNLOAD="https://download.oracle.com/graalvm/23/latest/graalvm-jdk-23_linux-x64_bin.tar.gz"

#
# The `make_dirs` function is designed to create necessary directories inside the mounted
# root filesystem. Note that the actual directory creation commands are currently commented out.
#
function make_dirs() {
  log "Creating directories." # if needed
  mkdir -p "repo"
  sudo mkdir -p "/mnt/rootfs/java"
  sudo mkdir -p "/mnt/rootfs/graal"
}

#
# The `jdk` function downloads the specified OpenJDK version for Linux, extracts it,
# copies it to the mounted root filesystem, and then renames the directory to `jdk`.
#
function jdk() {
  log "#"
  log "# OpenJDK $JAVA_VERSION"
  log "#"

  # Check if the file already exists
  if [ ! -f "repo/openjdk-${JAVA_VERSION}_linux-x64_bin.tar.gz" ]; then
    log "Downloading OpenJDK ${JAVA_VERSION}..."
    sudo wget "${JAVA_DOWNLOAD}" -O "repo/openjdk-${JAVA_VERSION}_linux-x64_bin.tar.gz"
  else
    log "repo/openjdk-${JAVA_VERSION}_linux-x64_bin.tar.gz already exists. Skipping download."
  fi

  # Extract the tarball
  log "Extracting tarball"
  sudo tar xvf "repo/openjdk-${JAVA_VERSION}_linux-x64_bin.tar.gz"
  sudo mv -fv "./jdk-${JAVA_VERSION}" "/mnt/rootfs/java"
}

#
# The `graal` function downloads the specified GraalVM version for Linux, extracts it,
# and copies it to the mounted root filesystem.
#
function graal() {
  log "#"
  log "# GraalVM 23"
  log "#"

  # Check if the file already exists
  if [ ! -f "repo/graalvm-jdk-23_linux-x64_bin.tar.gz" ]; then
    log "Downloading GraalVM 23..."
    sudo wget "${GRAAL_DOWNLOAD}" -O "repo/graalvm-jdk-23_linux-x64_bin.tar.gz"
  else
    log "repo/graalvm-jdk-23_linux-x64_bin.tar.gz already exists. Skipping download."
  fi
  # Extract the tarball
  log "Extracting tarball"
  sudo tar xvf "repo/graalvm-jdk-23_linux-x64_bin.tar.gz"
  sudo mv -fv "./graalvm-jdk-23.0.2+7.1/" "/mnt/rootfs/graalvm"
}

#
# The `felix` function downloads the specified Apache Felix framework and installs it
# in the correct location within the mounted root filesystem.
#
function felix() {
  log "#"
  log "# Apache Felix v${FELIX_VERSION}"
  log "#"

  # Check if the file already exists
  if [ ! -f "repo/org.apache.felix.main.distribution-${FELIX_VERSION}.zip" ]; then
    log "Downloading Apache Felix v${FELIX_VERSION}..."
    sudo wget "${FELIX_DOWNLOAD}" -O "repo/org.apache.felix.main.distribution-${FELIX_VERSION}.zip"
  else
    log "repo/org.apache.felix.main.distribution-${FELIX_VERSION}.zip already exists. Skipping download."
  fi

  sudo unzip -o "repo/org.apache.felix.main.distribution-${FELIX_VERSION}.zip"
  sudo mv -fv "./felix-framework-${FELIX_VERSION}" "/mnt/rootfs/opt/felix"
}

#
# The `activemq` function downloads the specified Apache ActiveMQ version and installs it
# in the correct location within the mounted root filesystem.
#
function activemq() {
  log "#"
  log "# Apache ActiveMQ v${ACTIVEMQ_VERSION}"
  log "#"

  # Check if the file already exists
  if [ ! -f "repo/apache-activemq-${ACTIVEMQ_VERSION}-bin.tar.gz" ]; then
    log "Downloading Apache ActiveMQ v${ACTIVEMQ_VERSION}..."
    sudo wget "${ACTIVEMQ_DOWNLOAD}" -O "repo/apache-activemq-${ACTIVEMQ_VERSION}-bin.tar.gz"
  else
    log "repo/apache-activemq-${ACTIVEMQ_VERSION}-bin.tar.gz already exists. Skipping download."
  fi

  # Extract the tarball
  sudo tar xvf "repo/apache-activemq-${ACTIVEMQ_VERSION}-bin.tar.gz"
  sudo mv -fv "./apache-activemq-${ACTIVEMQ_VERSION}" "/mnt/rootfs/opt/felix"
}

#
# The `copy_files` function copies specific files into the mounted root filesystem.
#
function copy_files() {
  log "Copying necessary files..."
  jdk
#  graal
#  activemq
#  felix
}

#
# The `cleanup_litter` function removes temporary files and directories created during the process.
#
function cleanup_litter() {
  log "Cleaning up."
  sudo rm -rf "./jdk-23.0.2"
  sudo rm -rf "./apache-activemq-6.1.5"
  sudo rm -rf "./felix-framework-7.0.5"
  sudo rm -rf "./graalvm-jdk-23.0.2+7.1"
  sudo sync
}

#
# The `main` function orchestrates the process.
#
function main() {
  mount_rootfs # Provide the disk image path dynamically when calling this function
  make_dirs
  copy_files
  cleanup_litter
  unmount_rootfs
}

main