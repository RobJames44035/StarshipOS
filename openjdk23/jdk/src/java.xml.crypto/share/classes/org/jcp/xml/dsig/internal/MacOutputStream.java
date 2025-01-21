/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package org.jcp.xml.dsig.internal;

import java.io.ByteArrayOutputStream;

import javax.crypto.Mac;

/**
 * Derived from Apache sources and changed to use Mac objects instead of
 * com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithm objects.
 *
 *
 */
public class MacOutputStream extends ByteArrayOutputStream {
    private final Mac mac;

    public MacOutputStream(Mac mac) {
        this.mac = mac;
    }

    @Override
    public void write(int arg0) {
        super.write(arg0);
        mac.update((byte) arg0);
    }

    @Override
    public void write(byte[] arg0, int arg1, int arg2) {
        super.write(arg0, arg1, arg2);
        mac.update(arg0, arg1, arg2);
    }
}
