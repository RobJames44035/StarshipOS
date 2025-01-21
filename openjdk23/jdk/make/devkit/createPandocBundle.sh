#!/bin/bash -e
#
# StarshipOS Copyright (c) 2017-2025. R.A. James
#

TMPDIR=`mktemp -d -t pandocbundle-XXXX`
trap "rm -rf \"$TMPDIR\"" EXIT

ORIG_DIR=`pwd`
cd "$TMPDIR"
PANDOC_VERSION=2.19.2
PACKAGE_VERSION=1.0
TARGET_PLATFORM=linux_x64
if [ $# = 1 ]; then
  TARGET_PLATFORM="$1"
fi

PANDOC_EXE="pandoc"
PANDOC_PATH="bin/$PANDOC_EXE"
if [[ $TARGET_PLATFORM == linux_x64 ]] ; then
  PANDOC_PLATFORM=linux-amd64
  PANDOC_SUFFIX=tar.gz
elif [[ $TARGET_PLATFORM == linux_aarch64 ]] ; then
  PANDOC_PLATFORM=linux-arm64
  PANDOC_SUFFIX=tar.gz
elif [[ $TARGET_PLATFORM == macosx_x64 ]] ; then
  PANDOC_PLATFORM=macOS
  PANDOC_SUFFIX=zip
elif [[ $TARGET_PLATFORM == windows_x64 ]] ; then
  PANDOC_PLATFORM=windows-x86_64
  PANDOC_SUFFIX=zip
  PANDOC_EXE="pandoc.exe"
  PANDOC_PATH="$PANDOC_EXE"
else
  echo "Unknown platform"
  exit 1
fi
BUNDLE_NAME=pandoc-$TARGET_PLATFORM-$PANDOC_VERSION+$PACKAGE_VERSION.tar.gz

wget https://github.com/jgm/pandoc/releases/download/$PANDOC_VERSION/pandoc-$PANDOC_VERSION-$PANDOC_PLATFORM.$PANDOC_SUFFIX

mkdir tmp
cd tmp
if [[ $PANDOC_SUFFIX == zip ]]; then
  unzip ../pandoc-$PANDOC_VERSION-$PANDOC_PLATFORM.$PANDOC_SUFFIX
else
  tar xzf ../pandoc-$PANDOC_VERSION-$PANDOC_PLATFORM.$PANDOC_SUFFIX
fi
cd ..

mkdir pandoc
cp tmp/pandoc-$PANDOC_VERSION/$PANDOC_PATH pandoc
chmod +x pandoc/$PANDOC_EXE

tar -cvzf ../$BUNDLE_NAME pandoc
cp ../$BUNDLE_NAME "$ORIG_DIR"
