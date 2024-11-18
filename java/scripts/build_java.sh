#!/bin/sh
# shellcheck disable=SC2164
# shellcheck disable=SC2162

# Constants
# shellcheck disable=SC2034
JDK_URL=""
JDK_ARCHIVE="openjdk-23.0.1_linux-x64_bin"
echo "$PWD"

#BOOT_JDK="/home/rajames/PROJECTS/StarshipOS/jdk-23.0.1"

# Step 1: Download JDK archive if it does not exist
#if [ ! -f "$JDK_ARCHIVE" ]; then
  echo "Downloading JDK archive..."
  wget --progress=bar "https://download.java.net/java/GA/jdk23.0.1/c28985cbf10d4e648e4004050f8781aa/11/GPL/openjdk-23.0.1_linux-x64_bin.tar.gz"
  echo "Unpacking JDK archive..."
  tar -xzfv "openjdk-23.0.1_linux-x64_bin.tar.gz"

#else
fi

# Step 2: Unpack the archive if the directory does not exist
#if [ ! -d "$EXTRACTED_DIR" ]; then
#  echo "Unpacking JDK archive..."
#  mkdir -p $JDK_ARCHIVE
#  tar -xzfv "openjdk-23.0.1_linux-x64_bin.tar.gz"
#else
#  echo "JDK directory already exists."
#fi

# Configuration and build
#if [ ! -d build ]; then
#  echo "Configuring build with boot JDK..."
#echo "$PWD"
#echo "$CWD"
#read -p "This is a pause. Hit a key!"
#  ./configure --with-boot-jdk="$BOOT_JDK"
#  cd ./jdk
#  echo "Cleaning previous builds if any..."
#  make clean
#  echo "Building JDK..."
#  make all
#echo "$PWD"
#echo "$CWD"
#read -p "This is a pause. Hit a key!"
#
##  echo "Copying build to ../build directory..."
##  cp ./jdk/build build
#fi

# Step 3: Clean up JDK archive and extracted directory
#echo "Cleaning up JDK archive and unpacked files..."
#rm -f "$JDK_ARCHIVE"
#rm -rf "$EXTRACTED_DIR"
echo "$PWD"
echo "$CWD"
# shellcheck disable=SC3045
read -p "This is a pause. Hit a key!"


echo "Build process complete."
