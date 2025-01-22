#! /bin/sh

#
# StarshipOS Copyright (c) 2020-2025. R.A. James
#

javac -d . ../../../../../../../make/jdk/src/classes/build/tools/spp/Spp.java

genBin() {
  for MS in "Heap" "Direct"
    do
      for SWAP in "Swap" ""
      do
        for RO in "RO" ""
        do
        extraArgs=""
        if [ "$RO" == "RO" ] ; then
          extraArgs="-KRO"
        fi
        java build.tools.spp.Spp -be -nel -K$1 -Dtype=$1 -DType=$2 -DFulltype=$3 \
              $extraArgs \
              -Kview \
              -DMs=$MS \
              -Dms=`echo "$MS" | awk '{print tolower($0)}'` \
              -DSWAP=$SWAP \
              -DRO=$RO \
              -DCarrierBW=$4 \
              -i$5 \
              -o$out
        done
      done
    done
}

gen() {
    out=$2Buffers.java
    rm -f $out
    java build.tools.spp.Spp -be -nel -K$1 -Dtype=$1 -DType=$2 -DFulltype=$3 \
          -DCarrierBW=$4 -iX-Buffers.java.template -o$out

    java build.tools.spp.Spp -be -nel -K$1 -Dtype=$1 -DType=$2 -DFulltype=$3 \
          -DMs=Heap -Dms=heap -DSWAP="" -DRO="" -iX-Buffers-bin.java.template -o$out

    if [ "$1" == "byte" ] ; then
      genBin $1 $2 $3 $4 X-ByteBuffers-bin.java.template
      genBin char Char Character 2 X-ByteBuffers-bin.java.template
      genBin short Short Short 2 X-ByteBuffers-bin.java.template
      genBin int Int Integer 4 X-ByteBuffers-bin.java.template
      genBin long Long Long 8 X-ByteBuffers-bin.java.template
      genBin float Float Float 4 X-ByteBuffers-bin.java.template
      genBin double Double Double 8 X-ByteBuffers-bin.java.template
    else
      genBin $1 $2 $3 $4 X-Buffers-bin.java.template
    fi

    printf "}\n" >> $out
}

gen byte Byte Byte 1
gen char Char Character 2
gen short Short Short 2
gen int Int Integer 4
gen long Long Long 8
gen float Float Float 4
gen double Double Double 8
