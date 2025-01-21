/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xml.internal.security.keys.storage.implementations;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import com.sun.org.apache.xml.internal.security.keys.storage.StorageResolverException;
import com.sun.org.apache.xml.internal.security.keys.storage.StorageResolverSpi;

/**
 * Makes the Certificates from a JAVA {@link KeyStore} object available to the
 * {@link com.sun.org.apache.xml.internal.security.keys.storage.StorageResolver}.
 */
public class KeyStoreResolver extends StorageResolverSpi {

    private static final com.sun.org.slf4j.internal.Logger LOG =
        com.sun.org.slf4j.internal.LoggerFactory.getLogger(KeyStoreResolver.class);

    /** Field keyStore */
    private final KeyStore keyStore;

    /**
     * Constructor KeyStoreResolver
     *
     * @param keyStore is the keystore which contains the Certificates
     * @throws StorageResolverException
     */
    public KeyStoreResolver(KeyStore keyStore) throws StorageResolverException {
        this.keyStore = keyStore;
        // Do a quick check on the keystore
        try {
            keyStore.aliases();
        } catch (KeyStoreException ex) {
            throw new StorageResolverException(ex);
        }
    }

    /** {@inheritDoc} */
    @Override
    public Iterator<Certificate> getIterator() {
        return new KeyStoreIterator(this.keyStore);
    }

    /**
     * Class KeyStoreIterator
     */
    static class KeyStoreIterator implements Iterator<Certificate> {

        private final List<Certificate> certs;

        private int i;

        /**
         * Constructor KeyStoreIterator
         *
         * @param keyStore
         */
        public KeyStoreIterator(KeyStore keyStore) {

            List<Certificate> tmpCerts = new ArrayList<>();
            try {
                Enumeration<String> aliases = keyStore.aliases();
                while (aliases.hasMoreElements()) {
                    String alias = aliases.nextElement();
                    Certificate cert = keyStore.getCertificate(alias);
                    if (cert != null) {
                        tmpCerts.add(cert);
                    }
                }
            } catch (KeyStoreException ex) {
                LOG.debug("Error reading certificates: {}", ex.getMessage());
            }

            certs = Collections.unmodifiableList(tmpCerts);
            this.i = 0;
        }

        /** {@inheritDoc} */
        @Override
        public boolean hasNext() {
            return this.i < this.certs.size();
        }

        /** {@inheritDoc} */
        @Override
        public Certificate next() {
            if (hasNext()) {
                return this.certs.get(this.i++);
            }

            throw new NoSuchElementException();
        }

        /**
         * Method remove
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException("Can't remove keys from KeyStore");
        }

    }

}
