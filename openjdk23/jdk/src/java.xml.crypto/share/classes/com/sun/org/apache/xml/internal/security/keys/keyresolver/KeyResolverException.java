/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xml.internal.security.keys.keyresolver;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;

public class KeyResolverException extends XMLSecurityException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor KeyResolverException
     *
     */
    public KeyResolverException() {
        super();
    }

    public KeyResolverException(Exception ex) {
        super(ex);
    }

    /**
     * Constructor KeyResolverException
     *
     * @param msgID
     */
    public KeyResolverException(String msgID) {
        super(msgID);
    }

    /**
     * Constructor KeyResolverException
     *
     * @param msgID
     * @param exArgs
     */
    public KeyResolverException(String msgID, Object[] exArgs) {
        super(msgID, exArgs);
    }

    /**
     * Constructor KeyResolverException
     *
     * @param originalException
     * @param msgID
     */
    public KeyResolverException(Exception originalException, String msgID) {
        super(originalException, msgID);
    }

    @Deprecated
    public KeyResolverException(String msgID, Exception originalException) {
        this(originalException, msgID);
    }

    /**
     * Constructor KeyResolverException
     *
     * @param originalException
     * @param msgID
     * @param exArgs
     */
    public KeyResolverException(Exception originalException, String msgID, Object[] exArgs) {
        super(originalException, msgID, exArgs);
    }

    @Deprecated
    public KeyResolverException(String msgID, Object[] exArgs, Exception originalException) {
        this(originalException, msgID, exArgs);
    }
}
