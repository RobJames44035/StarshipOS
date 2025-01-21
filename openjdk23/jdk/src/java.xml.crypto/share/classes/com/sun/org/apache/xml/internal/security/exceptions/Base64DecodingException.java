/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xml.internal.security.exceptions;

/**
 * This Exception is thrown if decoding of Base64 data fails.
 *
 */
public class Base64DecodingException extends XMLSecurityException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor Base64DecodingException
     *
     */
    public Base64DecodingException() {
        super();
    }

    /**
     * Constructor Base64DecodingException
     *
     * @param msgID
     */
    public Base64DecodingException(String msgID) {
        super(msgID);
    }

    /**
     * Constructor Base64DecodingException
     *
     * @param msgID
     * @param exArgs
     */
    public Base64DecodingException(String msgID, Object[] exArgs) {
        super(msgID, exArgs);
    }

    /**
     * Constructor Base64DecodingException
     *
     * @param originalException
     * @param msgID
     */
    public Base64DecodingException(Exception originalException, String msgID) {
        super(originalException, msgID);
    }

    @Deprecated
    public Base64DecodingException(String msgID, Exception originalException) {
        this(originalException, msgID);
    }

    /**
     * Constructor Base64DecodingException
     *
     * @param originalException
     * @param msgID
     * @param exArgs
     */
    public Base64DecodingException(Exception originalException, String msgID, Object[] exArgs) {
        super(originalException, msgID, exArgs);
    }

    @Deprecated
    public Base64DecodingException(String msgID, Object[] exArgs, Exception originalException) {
        this(originalException, msgID, exArgs);
    }

}
