/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.xni;

/**
 * This exception is the base exception of all XNI exceptions. It
 * can be constructed with an error message or used to wrap another
 * exception object.
 * <p>
 * <strong>Note:</strong> By extending the Java
 * <code>RuntimeException</code>, XNI handlers and components are
 * not required to catch XNI exceptions but may explicitly catch
 * them, if so desired.
 *
 * @author Andy Clark, IBM
 *
 */
public class XNIException
    extends RuntimeException {

    /** Serialization version. */
    static final long serialVersionUID = 9019819772686063775L;

    //
    // Data
    //

    /** The wrapped exception. */
    private Exception fException;

    //
    // Constructors
    //

    /**
     * Constructs an XNI exception with a message.
     *
     * @param message The exception message.
     */
    public XNIException(String message) {
        super(message);
    } // <init>(String)

    /**
     * Constructs an XNI exception with a wrapped exception.
     *
     * @param exception The wrapped exception.
     */
    public XNIException(Exception exception) {
        super(exception.getMessage());
        fException = exception;
    } // <init>(Exception)

    /**
     * Constructs an XNI exception with a message and wrapped exception.
     *
     * @param message The exception message.
     * @param exception The wrapped exception.
     */
    public XNIException(String message, Exception exception) {
        super(message);
        fException = exception;
    } // <init>(Exception,String)

    //
    // Public methods
    //

    /** Returns the wrapped exception. */
    public Exception getException() {
        return fException;
    } // getException():Exception

    public Throwable getCause() {
       return fException;
    }
} // class QName
