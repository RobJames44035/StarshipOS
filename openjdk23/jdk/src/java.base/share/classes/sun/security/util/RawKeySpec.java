/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package sun.security.util;

import java.security.spec.KeySpec;

/**
 * This is a KeySpec that is used to specify a key by its byte array implementation.
 * It is intended to be used in testing algorithms where the algorithm specification
 * describes the key in this form.
 */
public class RawKeySpec implements KeySpec {
    private final byte[] keyArr;
    /**
     * The sole constructor.
     * @param key contains the key as a byte array
     */
    public RawKeySpec(byte[] key) {
        keyArr = key.clone();
    }

    /**
     * Getter function.
     * @return a copy of the key bits
     */
    public byte[] getKeyArr() {
        return keyArr.clone();
    }
}
