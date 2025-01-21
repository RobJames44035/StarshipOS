/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xml.internal.security.transforms;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;

/**
 *
 */
public class TransformationException extends XMLSecurityException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor TransformationException
     *
     */
    public TransformationException() {
        super();
    }

    public TransformationException(Exception ex) {
        super(ex);
    }

    /**
     * Constructor TransformationException
     *
     * @param msgID
     */
    public TransformationException(String msgID) {
        super(msgID);
    }

    /**
     * Constructor TransformationException
     *
     * @param msgID
     * @param exArgs
     */
    public TransformationException(String msgID, Object[] exArgs) {
        super(msgID, exArgs);
    }

    /**
     * Constructor TransformationException
     *
     * @param originalException
     * @param msgID
     */
    public TransformationException(Exception originalException, String msgID) {
        super(originalException, msgID);
    }

    @Deprecated
    public TransformationException(String msgID, Exception originalException) {
        this(originalException, msgID);
    }

    /**
     * Constructor TransformationException
     *
     * @param originalException
     * @param msgID
     * @param exArgs
     */
    public TransformationException(Exception originalException, String msgID, Object[] exArgs) {
        super(originalException, msgID, exArgs);
    }

    @Deprecated
    public TransformationException(String msgID, Object[] exArgs, Exception originalException) {
        this(originalException, msgID, exArgs);
    }
}
