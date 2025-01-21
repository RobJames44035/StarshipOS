/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package com.sun.crypto.provider;

import java.nio.ByteBuffer;

/**
 * This interface allows GHASH.java and GCTR.java to easily operate to
 * better operate with GaloisCounterMode.java
 */

public interface GCM {
    int update(byte[] in, int inOfs, int inLen, byte[] out, int outOfs);
    int update(byte[] in, int inOfs, int inLen, ByteBuffer dst);
    int update(ByteBuffer src, ByteBuffer dst);
    int doFinal(byte[] in, int inOfs, int inLen, byte[] out, int outOfs);
    int doFinal(ByteBuffer src, ByteBuffer dst);
}
