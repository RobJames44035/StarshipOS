/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xml.internal.security.c14n;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;

public class InvalidCanonicalizerException extends XMLSecurityException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor InvalidCanonicalizerException
     *
     */
    public InvalidCanonicalizerException() {
        super();
    }

    /**
     * Constructor InvalidCanonicalizerException
     *
     * @param msgID
     */
    public InvalidCanonicalizerException(String msgID) {
        super(msgID);
    }

    /**
     * Constructor InvalidCanonicalizerException
     *
     * @param msgID
     * @param exArgs
     */
    public InvalidCanonicalizerException(String msgID, Object[] exArgs) {
        super(msgID, exArgs);
    }

    /**
     * Constructor InvalidCanonicalizerException
     *
     * @param originalException
     * @param msgID
     */
    public InvalidCanonicalizerException(Exception originalException, String msgID) {
        super(originalException, msgID);
    }

    @Deprecated
    public InvalidCanonicalizerException(String msgID, Exception originalException) {
        this(originalException, msgID);
    }

    /**
     * Constructor InvalidCanonicalizerException
     *
     * @param originalException
     * @param msgID
     * @param exArgs
     */
    public InvalidCanonicalizerException(
        Exception originalException, String msgID, Object[] exArgs
    ) {
        super(originalException, msgID, exArgs);
    }

    @Deprecated
    public InvalidCanonicalizerException(String msgID, Object[] exArgs, Exception originalException) {
        this(originalException, msgID, exArgs);
    }
}
