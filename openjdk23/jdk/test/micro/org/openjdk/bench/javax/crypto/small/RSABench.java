/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
package org.openjdk.bench.javax.crypto.small;

import org.openjdk.jmh.annotations.Param;

public class RSABench extends org.openjdk.bench.javax.crypto.full.RSABench {

    @Param({"RSA/ECB/NoPadding"})
    protected String algorithm;

    @Param({"32"})
    protected int dataSize;

    @Param({"2048"})
    protected int keyLength;

}
