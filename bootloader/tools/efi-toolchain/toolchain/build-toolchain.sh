#!/bin/sh

# Set up directories
DL_DIR="$(pwd)/dl"
SRC_DIR="$(pwd)/toolchain-src"
TARBALL="$DL_DIR/gnu-efi-3.0.18.tar.bz2"
URL="https://sourceforge.net/projects/gnu-efi/files/latest/download"

# Ensure the download directory exists
mkdir -p "$DL_DIR"

# Download the tarball if it's not already there
if [ ! -f "$TARBALL" ]; then
    echo "Downloading GNU-EFI..."
    wget -O "$TARBALL" "$URL"
else
    echo "Tarball already exists. Skipping download."
fi

# Verify if the tarball was downloaded successfully
if [ ! -s "$TARBALL" ]; then
    echo "Download failed or the tarball is empty. Exiting."
    exit 1
fi

# Ensure the source directory exists
mkdir -p "$SRC_DIR"

# Extract the tarball
echo "Extracting GNU-EFI..."
tar -xjf "$TARBALL" -C "$SRC_DIR" || { echo "Extraction failed!"; exit 1; }

# Navigate to the extracted source directory
EFI_SRC_DIR=$(find "$SRC_DIR" -maxdepth 1 -type d -name "gnu-efi*" | head -n 1)
if [ -z "$EFI_SRC_DIR" ]; then
    echo "Failed to locate extracted source. Exiting."
    exit 1
fi

cd "$EFI_SRC_DIR" || exit 1

# Build the toolchain
echo "Building GNU-EFI..."
make || { echo "Build failed!"; exit 1; }

echo "Build completed successfully."

# *** Place libraries and headers in the expected directory ***
TARGET_DIR="$EFI_SRC_DIR/x86_64/lib/"
EXPECTED_OUTPUT="${EFI_SRC_DIR}/x86_64/lib/libefi.a"

if [ ! -f "$EXPECTED_OUTPUT" ]; then
    echo "Error: $EXPECTED_OUTPUT not found after build!"
    exit 1
fi

echo "Toolchain build complete. Libraries are now located at: $TARGET_DIR"
