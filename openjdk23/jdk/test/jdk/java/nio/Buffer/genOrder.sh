#! /bin/sh

#
# StarshipOS Copyright (c) 2002-2025. R.A. James
#

javac -d . ../../../../../make/jdk/src/classes/build/tools/spp/Spp.java
gen() {
    out=Order$2.java
    rm -f $out
    java  build.tools.spp.Spp -K$1 -Dtype=$1 -DType=$2 -DFulltype=$3 -iOrder-X.java.template -o$out
}

gen char Char Character
gen short Short Short
gen int Int Integer
gen long Long Long
gen float Float Float
gen double Double Double

rm -rf build
