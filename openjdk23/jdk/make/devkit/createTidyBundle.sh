#!/bin/bash
#
# StarshipOS Copyright (c) 2024-2025. R.A. James
#

# Creates a tidy bundle in the build directory. A dependency that can be
# used to validate and correct HTML.

# wget, cmake and gcc are required to build tidy.

set -e

GITHUB_USER="htacg"
REPO_NAME="tidy-html5"
COMMIT_HASH="d08ddc2860aa95ba8e301343a30837f157977cba"
SCRIPT_DIR="$(cd "$(dirname $0)" > /dev/null && pwd)"
INSTALL_PREFIX="${SCRIPT_DIR}/../../build/tidy/tidy/"
BUILD_DIR="build/cmake"

OS_NAME=$(uname -s)
OS_ARCH=$(uname -m)

DOWNLOAD_URL="https://github.com/$GITHUB_USER/$REPO_NAME/archive/$COMMIT_HASH.tar.gz"
OUTPUT_FILE="$REPO_NAME-$COMMIT_HASH.tar.gz"

wget "$DOWNLOAD_URL" -O "$OUTPUT_FILE"

tar -xzf "$OUTPUT_FILE"
rm -rf "$OUTPUT_FILE"

SRC_DIR="$REPO_NAME-$COMMIT_HASH"

mkdir -p "$SRC_DIR/$BUILD_DIR"
cd "$SRC_DIR/$BUILD_DIR"

case $OS_NAME in
  Linux|Darwin)
    echo "Building Tidy HTML5 for Unix-like platform ($OS_NAME)..."

    CMAKE_ARCH_OPTIONS=""
    if [ "$OS_NAME" == "Darwin" ]; then
      if [[ "$OS_ARCH" == "arm64" || "$OS_ARCH" == "x86_64" ]]; then
        CMAKE_ARCH_OPTIONS="-DCMAKE_OSX_ARCHITECTURES=x86_64;arm64"
      fi
    fi

    cmake ../.. -DCMAKE_BUILD_TYPE=Release -DCMAKE_INSTALL_PREFIX="$INSTALL_PREFIX" $CMAKE_ARCH_OPTIONS
    make install
    ;;

  *)
    echo "Unsupported OS: $OS_NAME"
    exit 1
    ;;
esac

cd "$SCRIPT_DIR"
rm -rf "$SRC_DIR"

cd "$INSTALL_PREFIX.."
PACKAGED_FILE="tidy-html5.tar.gz"

tar -czvf "$PACKAGED_FILE" -C "$INSTALL_PREFIX.." tidy

echo "Created $INSTALL_PREFIX..$PACKAGED_FILE"
