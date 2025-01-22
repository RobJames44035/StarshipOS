#!/bin/bash -e
#
# StarshipOS Copyright (c) 2018-2025. R.A. James
#

JMH_VERSION=1.37
COMMONS_MATH3_VERSION=3.6.1
JOPT_SIMPLE_VERSION=5.0.4
MAVEN_MIRROR=${MAVEN_MIRROR:-https://repo.maven.apache.org/maven2}

BUNDLE_NAME=jmh-$JMH_VERSION.tar.gz

SCRIPT_DIR="$(cd "$(dirname $0)" > /dev/null && pwd)"
BUILD_DIR="${SCRIPT_DIR}/../../build/jmh"
JAR_DIR="$BUILD_DIR/jars"

mkdir -p $BUILD_DIR $JAR_DIR
cd $JAR_DIR
rm -f *

fetchJar() {
  url="${MAVEN_MIRROR}/$1/$2/$3/$2-$3.jar"
  if command -v curl > /dev/null; then
      curl -OL --fail $url
  elif command -v wget > /dev/null; then
      wget $url
  else
      echo "Could not find either curl or wget"
      exit 1
  fi
}

fetchJar org/apache/commons commons-math3 $COMMONS_MATH3_VERSION
fetchJar net/sf/jopt-simple jopt-simple $JOPT_SIMPLE_VERSION
fetchJar org/openjdk/jmh jmh-core $JMH_VERSION
fetchJar org/openjdk/jmh jmh-generator-annprocess $JMH_VERSION

tar -cvzf ../$BUNDLE_NAME *

echo "Created $BUILD_DIR/$BUNDLE_NAME"
