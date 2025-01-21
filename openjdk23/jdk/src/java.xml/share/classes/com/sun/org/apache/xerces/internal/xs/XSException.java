/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.xs;

/**
 * The XML Schema API operations only raise exceptions in "exceptional"
 * circumstances, i.e., when an operation is impossible to perform (either
 * for logical reasons, because data is lost, or because the implementation
 * has become unstable).
 * <p>Implementations should raise other exceptions under other circumstances.
 * <p>Some languages and object systems do not support the concept of
 * exceptions. For such systems, error conditions may be indicated using
 * native error reporting mechanisms. For some bindings, for example,
 * methods may return error codes similar to those listed in the
 * corresponding method descriptions.
 */
public class XSException extends RuntimeException {

    /** Serialization version. */
    static final long serialVersionUID = 3111893084677917742L;

    public XSException(short code, String message) {
       super(message);
       this.code = code;
    }
    public short   code;
    // ExceptionCode
    /**
     * If the implementation does not support the requested type of object or
     * operation.
     */
    public static final short NOT_SUPPORTED_ERR         = 1;
    /**
     * If index or size is negative, or greater than the allowed value
     */
    public static final short INDEX_SIZE_ERR            = 2;

}
