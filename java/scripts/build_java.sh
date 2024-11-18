#!/bin/bash
# shellcheck disable=SC2164
# shellcheck disable=SC2162

# Constants
JDK_URL="https://example.com/path/to/jdk-23.0.1.tar.gz"
JDK_ARCHIVE="jdk-23.0.1.tar.gz"
JDK_DIR="jdk-23.0.1"
EXTRACTED_DIR="/home/rajames/PROJECTS/StarshipOS/$JDK_DIR"
BOOT_JDK="/home/rajames/PROJECTS/StarshipOS/jdk-23.0.1"

echo "$PWD"
echo "$CWD"
read -p "This is a psuse. Hit a key!"

# Step 1: Download JDK archive if it does not exist
if [ ! -f "$JDK_ARCHIVE" ]; then
  echo "Downloading JDK archive..."
  wget "$JDK_URL" -O "$JDK_ARCHIVE"
else
  echo "JDK archive already exists."
fi
echo "$PWD"
echo "$CWD"
read -p "This is a psuse. Hit a key!"

# Step 2: Unpack the archive if the directory does not exist
if [ ! -d "$EXTRACTED_DIR" ]; then
  echo "Unpacking JDK archive..."
  tar -xzvf "$JDK_ARCHIVE" -C $JDK_DIR
else
  echo "JDK directory already exists."
fi
echo "$PWD"
echo "$CWD"
read -p "This is a psuse. Hit a key!"

# Configuration and build
if [ ! -d build ]; then
  echo "Configuring build with boot JDK..."
  ./configure --with-boot-jdk="$BOOT_JDK"
  cd ./jdk
  echo "Cleaning previous builds if any..."
  make clean
  echo "Building JDK..."
  make all
echo "$PWD"
echo "$CWD"
read -p "This is a psuse. Hit a key!"

#  echo "Copying build to ../build directory..."
#  cp ./jdk/build build
fi

echo "$PWD"
echo "$CWD"
read -p "This is a psuse. Hit a key!"

# Step 3: Clean up JDK archive and extracted directory
#echo "Cleaning up JDK archive and unpacked files..."
#rm -f "$JDK_ARCHIVE"
#rm -rf "$EXTRACTED_DIR"
echo "$PWD"
echo "$CWD"
read -p "This is a psuse. Hit a key!"


echo "Build process complete."
