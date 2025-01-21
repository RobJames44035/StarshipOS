/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package org.w3c.dom.xpath;

/**
 * A new exception has been created for exceptions specific to these XPath
 * interfaces.
 *
 * See also <a href='https://www.w3.org/TR/DOM-Level-3-XPath/'>
 * Document Object Model (DOM) Level 3 XPath Specification, Version 1.0,
 * W3C Working Group Note 26 February 2004</a>
 * Note that this class came from Document Object Model (DOM) Level 3 XPath
 * Specification, Working Draft 20 August 2002 where the values of
 * {@link #INVALID_EXPRESSION_ERR} and {@link #TYPE_ERR}
 * are 1 and 2 respectively (instead of 51 and 52 as in the 2004 version).
 *
 * @since 1.4, DOM Level 3
 */
public class XPathException extends RuntimeException {
    private static final long serialVersionUID = 3471034171575979943L;

    public XPathException(short code, String message) {
       super(message);
       this.code = code;
    }
    public short   code;
    // XPathExceptionCode
    /**
     * If the expression has a syntax error or otherwise is not a legal
     * expression according to the rules of the specific
     * <code>XPathEvaluator</code> or contains specialized extension
     * functions or variables not supported by this implementation.
     */
    public static final short INVALID_EXPRESSION_ERR    = 1;
    /**
     * If the expression cannot be converted to return the specified type.
     */
    public static final short TYPE_ERR                  = 2;

}
