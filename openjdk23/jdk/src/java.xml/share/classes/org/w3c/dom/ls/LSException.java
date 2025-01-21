/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package org.w3c.dom.ls;

/**
 *  Parser or write operations may throw an <code>LSException</code> if the
 * processing is stopped. The processing can be stopped due to a
 * <code>DOMError</code> with a severity of
 * <code>DOMError.SEVERITY_FATAL_ERROR</code> or a non recovered
 * <code>DOMError.SEVERITY_ERROR</code>, or if
 * <code>DOMErrorHandler.handleError()</code> returned <code>false</code>.
 * <p ><b>Note:</b>  As suggested in the definition of the constants in the
 * <code>DOMError</code> interface, a DOM implementation may choose to
 * continue after a fatal error, but the resulting DOM tree is then
 * implementation dependent.
 * <p>See also the <a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-LS-20040407'>Document Object Model (DOM) Level 3 Load
and Save Specification</a>.
 *
 * @since 1.5
 */
public class LSException extends RuntimeException {
    private static final long serialVersionUID = 5371691160978884690L;

    public LSException(short code, String message) {
       super(message);
       this.code = code;
    }
    public short   code;
    // LSExceptionCode
    /**
     *  If an attempt was made to load a document, or an XML Fragment, using
     * <code>LSParser</code> and the processing has been stopped.
     */
    public static final short PARSE_ERR                 = 81;
    /**
     *  If an attempt was made to serialize a <code>Node</code> using
     * <code>LSSerializer</code> and the processing has been stopped.
     */
    public static final short SERIALIZE_ERR             = 82;

}
