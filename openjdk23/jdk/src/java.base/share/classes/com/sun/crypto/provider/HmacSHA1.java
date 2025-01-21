/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

package com.sun.crypto.provider;

import java.security.NoSuchAlgorithmException;

/**
 * This is an implementation of the HMAC-SHA1 algorithm.
 *
 * @author Jan Luehe
 */
public final class HmacSHA1 extends HmacCore {
    /**
     * Standard constructor, creates a new HmacSHA1 instance.
     */
    public HmacSHA1() throws NoSuchAlgorithmException {
        super("SHA1", 64);
    }
}
