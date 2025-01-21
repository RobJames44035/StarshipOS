/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

package jdk.internal.util.xml;

public class XMLStreamException extends Exception {
    @java.io.Serial
    private static final long serialVersionUID = 1L;


    protected Throwable nested;

    /**
     * Default constructor
     */
    public XMLStreamException() {
        super();
    }

    /**
     * Construct an exception with the associated message.
     *
     * @param msg the message to report
     */
    public XMLStreamException(String msg) {
        super(msg);
    }

    /**
     * Construct an exception with the associated exception
     *
     * @param th a nested exception
     */
    public XMLStreamException(Throwable th) {
        super(th);
        nested = th;
    }

    /**
     * Construct an exception with the associated message and exception
     *
     * @param th a nested exception
     * @param msg the message to report
     */
    public XMLStreamException(String msg, Throwable th) {
        super(msg, th);
        nested = th;
    }

    /**
     * Gets the nested exception.
     *
     * @return Nested exception
     */
    public Throwable getNestedException() {
        return nested;
    }
}
