#!/bin/bash
#
# StarshipOS Copyright (c) 2015-2025. R.A. James
#

# This script copies parts of an Xcode installation into a devkit suitable
# for building OpenJDK and OracleJDK. The installation Xcode_X.X.xip needs
# to be either installed or extracted using for example Archive Utility.
# The easiest way to accomplish this is to right click the file in Finder
# and choose "Open With -> Archive Utility", or possible typing
# "open Xcode_9.2.xip" in a terminal.
# erik.joelsson@oracle.com

USAGE="$0 <Xcode.app>"

if [ "$1" = "" ]; then
    echo $USAGE
    exit 1
fi

XCODE_APP="$1"
XCODE_APP_DIR_NAME="${XCODE_APP##*/}"

SCRIPT_DIR="$(cd "$(dirname $0)" > /dev/null && pwd)"
BUILD_DIR="${SCRIPT_DIR}/../../build/devkit"

# Find the version of Xcode
XCODE_VERSION="$($XCODE_APP/Contents/Developer/usr/bin/xcodebuild -version \
    | awk '/Xcode/ { print $2 }' )"
SDK_VERSION="$(ls $XCODE_APP/Contents/Developer/Platforms/MacOSX.platform/Developer/SDKs \
    | grep [0-9] | sort -r | head -n1 | sed 's/\.sdk//')"

DEVKIT_ROOT="${BUILD_DIR}/Xcode${XCODE_VERSION}-${SDK_VERSION}"
DEVKIT_BUNDLE="${DEVKIT_ROOT}.tar.gz"

echo "Xcode version: $XCODE_VERSION"
echo "SDK version: $SDK_VERSION"
echo "Creating devkit in $DEVKIT_ROOT"

mkdir -p $DEVKIT_ROOT

################################################################################
# Copy the relevant parts of Xcode.app, removing things that are both big and
# unnecessary for our purposes, without building an impossibly long exclude list.
EXCLUDE_DIRS=" \
    Contents/_CodeSignature \
    Contents/Applications \
    Contents/Resources \
    Contents/Library \
    Contents/XPCServices \
    Contents/OtherFrameworks \
    Contents/Developer/Documentation \
    Contents/Developer/usr/share \
    Contents/Developer/usr/libexec/git-core \
    Contents/Developer/usr/bin/git* \
    Contents/Developer/usr/bin/svn* \
    Contents/Developer/usr/lib/libgit* \
    Contents/Developer/usr/lib/libsvn* \
    Contents/Developer/Platforms/MacOSX.platform/Developer/SDKs/MacOSX.sdk/usr/share/man \
    Contents/Developer/Platforms/MacOSX.platform/Developer/SDKs/${SDK_VERSION}.sdk/usr/share/man \
    Contents/Developer/Platforms/MacOSX.platform/Developer/usr/share/man \
    Contents/Developer/Platforms/MacOSX.platform/usr \
    Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/share/man \
    Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/swift* \
    Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/lib/swift* \
    Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/lib/sourcekitd.framework \
    Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/libexec/swift* \
    Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/include/swift* \
    Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/arc \
    Platforms/AppleTVSimulator.platform \
    Platforms/iPhoneSimulator.platform \
    Platforms/WatchSimulator.platform \
    Contents/SharedFrameworks/ModelIO.framework \
    Contents/SharedFrameworks/XCSUI.framework \
    Contents/SharedFrameworks/SceneKit.framework \
    Contents/SharedFrameworks/XCBuild.framework \
    Contents/SharedFrameworks/GPUTools*.framework \
    Contents/SharedFrameworks/DNTDocumentationSupport.framework/Versions/A/Resources/external \
    $(cd $XCODE_APP && ls -d Contents/Developer/Platforms/* \
        | grep -v MacOSX.platform | grep -v WatchSimulator.platform) \
"

for ex in $EXCLUDE_DIRS; do
    EXCLUDE_ARGS="$EXCLUDE_ARGS --exclude=$ex"
done

echo "Copying Xcode.app..."
echo rsync -rlH $INCLUDE_ARGS $EXCLUDE_ARGS "$XCODE_APP/." $DEVKIT_ROOT/Xcode
rsync -rlH $INCLUDE_ARGS $EXCLUDE_ARGS "$XCODE_APP/." $DEVKIT_ROOT/Xcode

################################################################################

echo-info() {
    echo "$1" >> $DEVKIT_ROOT/devkit.info
}

echo "Generating devkit.info..."
rm -f $DEVKIT_ROOT/devkit.info
echo-info "# This file describes to configure how to interpret the contents of this devkit"
echo-info "DEVKIT_NAME=\"Xcode $XCODE_VERSION (devkit)\""
echo-info "DEVKIT_TOOLCHAIN_PATH=\"\$DEVKIT_ROOT/Xcode/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin:\$DEVKIT_ROOT/Xcode/Contents/Developer/usr/bin\""
echo-info "DEVKIT_SYSROOT=\"\$DEVKIT_ROOT/Xcode/Contents/Developer/Platforms/MacOSX.platform/Developer/SDKs/$SDK_VERSION.sdk\""
echo-info "DEVKIT_EXTRA_PATH=\"\$DEVKIT_TOOLCHAIN_PATH\""

################################################################################
# Copy this script

echo "Copying this script..."
cp $0 $DEVKIT_ROOT/

################################################################################
# Create bundle

echo "Creating bundle..."
GZIP=$(command -v pigz)
if [ -z "$GZIP" ]; then
    GZIP="gzip"
fi
(cd $DEVKIT_ROOT && tar c - . | $GZIP - > "$DEVKIT_BUNDLE")
