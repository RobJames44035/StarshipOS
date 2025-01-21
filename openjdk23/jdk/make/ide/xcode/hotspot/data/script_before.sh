#!/bin/bash
#
# StarshipOS Copyright (c) 2017-2025. R.A. James
#

echo "running script_before.sh"

readonly JDK_BUILD_PATH="..";
readonly JAVAC_LOCATE_PATTERN="images/jdk/bin/javac";
readonly HOTSPOT_TOUCH_FILE="../../../src/hotspot/os/posix/jvm_posix.cpp";

echo ">>>>>>> Making a copy of JDK ...";

javac_file_array=( $(find ${JDK_BUILD_PATH} | grep ${JAVAC_LOCATE_PATTERN}) );
javac_file=${javac_file_array[0]};
if [ -z ${javac_file} ] ; then
{
  echo ">>>>>>>   ERROR: could not locate ${JAVAC_LOCATE_PATTERN} (did you remember to do \"make images\"?)";
  exit 1;
}
fi

jdk_build_path=$(dirname $(dirname ${javac_file}));
if [ ! -f "build/${JAVAC_LOCATE_PATTERN}" ] ; then
{
  echo ">>>>>>>   Copying jdk over...";
  rsync -a "${jdk_build_path}" "build/";
}
fi

# the following files will be supplied by the Xcode build
rm -rf "build/jdk/lib/server/libjvm.dylib";
rm -rf "build/jdk/lib/server/libjvm.dylib.dSYM";

echo ">>>>>>> DONE";

echo ">>>>>>> Touching ${HOTSPOT_TOUCH_FILE} to force HotspotVM rebuilt";
if [ ! -f ${HOTSPOT_TOUCH_FILE} ] ; then
{
    echo ">>>>>>>   Cannot find ${HOTSPOT_TOUCH_FILE}";
    exit 1;
}
fi
touch ${HOTSPOT_TOUCH_FILE};

echo ">>>>>>> DONE";

echo ">>>>>>> Xcode should be building the HotspotVM now...";
