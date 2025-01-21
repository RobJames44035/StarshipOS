/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
package com.sun.crypto.provider;

import java.security.*;
import java.security.spec.*;
import java.util.Arrays;

// The alternate DHKEM implementation used by the Compliance.java test.
public class EvenKEMImpl extends DHKEM {

    public static boolean isEven(Key k) {
        return Arrays.hashCode(k.getEncoded()) % 2 == 0;
    }

    @Override
    public EncapsulatorSpi engineNewEncapsulator(
            PublicKey pk, AlgorithmParameterSpec spec, SecureRandom secureRandom)
            throws InvalidAlgorithmParameterException, InvalidKeyException {
        if (!isEven(pk)) throw new InvalidKeyException("Only accept even keys");
        return super.engineNewEncapsulator(pk, spec, secureRandom);
    }

    @Override
    public DecapsulatorSpi engineNewDecapsulator(
            PrivateKey sk, AlgorithmParameterSpec spec)
            throws InvalidAlgorithmParameterException, InvalidKeyException {
        if (!isEven(sk)) throw new InvalidKeyException("Only accept even keys");
        return super.engineNewDecapsulator(sk, spec);
    }
}
