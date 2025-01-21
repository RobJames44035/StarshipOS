/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package org.xml.sax;

/**
 * Exception class for an unsupported operation.
 *
 * <p>An XMLReader will throw this exception when it recognizes a
 * feature or property identifier, but cannot perform the requested
 * operation (setting a state or value).  Other SAX2 applications and
 * extensions may use this class for similar purposes.</p>
 *
 * @since 1.4, SAX 2.0
 * @author David Megginson
 * @see org.xml.sax.SAXNotRecognizedException
 */
public class SAXNotSupportedException extends SAXException
{

    /**
     * Construct a new exception with no message.
     */
    public SAXNotSupportedException ()
    {
        super();
    }


    /**
     * Construct a new exception with the given message.
     *
     * @param message The text message of the exception.
     */
    public SAXNotSupportedException (String message)
    {
        super(message);
    }

    // Added serialVersionUID to preserve binary compatibility
    static final long serialVersionUID = -1422818934641823846L;
}

// end of SAXNotSupportedException.java
