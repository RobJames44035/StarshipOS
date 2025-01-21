/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

package sun.security.ssl;

import java.util.List;
import java.util.Collections;

import java.security.*;
import java.security.KeyStore.*;

import javax.net.ssl.*;

abstract class KeyManagerFactoryImpl extends KeyManagerFactorySpi {

    X509ExtendedKeyManager keyManager;
    boolean isInitialized;

    KeyManagerFactoryImpl() {
        // empty
    }

    /**
     * Returns one key manager for each type of key material.
     */
    @Override
    protected KeyManager[] engineGetKeyManagers() {
        if (!isInitialized) {
            throw new IllegalStateException(
                        "KeyManagerFactoryImpl is not initialized");
        }
        return new KeyManager[] { keyManager };
    }

    // Factory for the SunX509 keymanager
    public static final class SunX509 extends KeyManagerFactoryImpl {

        @Override
        protected void engineInit(KeyStore ks, char[] password) throws
                KeyStoreException, NoSuchAlgorithmException,
                UnrecoverableKeyException {
            keyManager = new SunX509KeyManagerImpl(ks, password);
            isInitialized = true;
        }

        @Override
        protected void engineInit(ManagerFactoryParameters spec) throws
                InvalidAlgorithmParameterException {
            throw new InvalidAlgorithmParameterException(
                "SunX509KeyManager does not use ManagerFactoryParameters");
        }

    }

    // Factory for the X509 keymanager
    public static final class X509 extends KeyManagerFactoryImpl {

        @Override
        protected void engineInit(KeyStore ks, char[] password) throws
                KeyStoreException, NoSuchAlgorithmException,
                UnrecoverableKeyException {
            if (ks == null) {
                keyManager = new X509KeyManagerImpl(
                        Collections.emptyList());
            } else {
                try {
                    Builder builder = Builder.newInstance(ks,
                        new PasswordProtection(password));
                    keyManager = new X509KeyManagerImpl(builder);
                } catch (RuntimeException e) {
                    throw new KeyStoreException("initialization failed", e);
                }
            }
            isInitialized = true;
        }

        @Override
        protected void engineInit(ManagerFactoryParameters params) throws
                InvalidAlgorithmParameterException {
            if (!(params instanceof KeyStoreBuilderParameters)) {
                throw new InvalidAlgorithmParameterException(
                "Parameters must be instance of KeyStoreBuilderParameters");
            }

            List<Builder> builders =
                ((KeyStoreBuilderParameters)params).getParameters();
            keyManager = new X509KeyManagerImpl(builders);
            isInitialized = true;
        }

    }

}
