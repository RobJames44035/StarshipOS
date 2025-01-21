/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package sun.security.provider;

import java.security.SecureRandomParameters;
import java.security.SecureRandomSpi;

/**
 * Read ../../../../SpecTest.java for details.
 */
public class S extends SecureRandomSpi {

    protected AbstractDrbg impl;

    // This is not a DRBG.
    public static class S1 extends SecureRandomSpi {
        @Override
        protected void engineSetSeed(byte[] seed) {
        }

        @Override
        protected void engineNextBytes(byte[] bytes) {
        }

        @Override
        protected byte[] engineGenerateSeed(int numBytes) {
            return new byte[numBytes];
        }
    }

    // This is a weak DRBG. maximum strength is 128 and does
    // not support prediction resistance or reseed.
    public static class S2 extends S {
        public S2(SecureRandomParameters params) {
            impl = new Impl2(params);
        }
    }

    // This is a strong DRBG.
    public static class S3 extends S {
        public S3(SecureRandomParameters params) {
            impl = new Impl3(params);
        }
    }

    // AbstractDrbg Implementations

    static class Impl3 extends AbstractDrbg {

        public Impl3(SecureRandomParameters params) {
            supportPredictionResistance = true;
            supportReseeding = true;
            highestSupportedSecurityStrength = 192;
            mechName = "S3";
            algorithm = "SQUEEZE";
            configure(params);
        }

        protected void chooseAlgorithmAndStrength() {
            if (requestedInstantiationSecurityStrength < 0) {
                securityStrength = DEFAULT_STRENGTH;
            } else {
                securityStrength = requestedInstantiationSecurityStrength;
            }
            minLength = securityStrength / 8;
            maxAdditionalInputLength = maxPersonalizationStringLength = 100;
        }

        @Override
        protected void initEngine() {
        }

        @Override
        protected void instantiateAlgorithm(byte[] ei) {
        }

        @Override
        protected void generateAlgorithm(byte[] result, byte[] additionalInput) {
        }

        @Override
        protected void reseedAlgorithm(byte[] ei, byte[] additionalInput) {
        }
    }

    static class Impl2 extends Impl3 {
        public Impl2(SecureRandomParameters params) {
            super(null);
            mechName = "S2";
            highestSupportedSecurityStrength = 128;
            supportPredictionResistance = false;
            supportReseeding = false;
            configure(params);
        }
    }

    // Overridden SecureRandomSpi methods

    @Override
    protected void engineSetSeed(byte[] seed) {
        impl.engineSetSeed(seed);
    }

    @Override
    protected void engineNextBytes(byte[] bytes) {
        impl.engineNextBytes(bytes);
    }

    @Override
    protected byte[] engineGenerateSeed(int numBytes) {
        return impl.engineGenerateSeed(numBytes);
    }

    @Override
    protected void engineNextBytes(
            byte[] bytes, SecureRandomParameters params) {
        impl.engineNextBytes(bytes, params);
    }

    @Override
    protected void engineReseed(SecureRandomParameters params) {
        impl.engineReseed(params);
    }

    @Override
    protected SecureRandomParameters engineGetParameters() {
        return impl.engineGetParameters();
    }

    @Override
    public String toString() {
        return impl.toString();
    }
}
