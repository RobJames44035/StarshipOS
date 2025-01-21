/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */
package javax.xml.parsers.ptests;

import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Customized DefaultHandler used for SAXParseException testing.
 */
class MyErrorHandler extends DefaultHandler {
    /**
     * Flag whether any event was received.
     */
    private volatile boolean errorOccured;

    /**
     * Set no event received on constructor.
     */
    private MyErrorHandler() {
        errorOccured = false;
    }

    /**
     * Factory method to create a MyErrorHandler instance.
     * @return a MyErrorHandler instance.
     */
    public static MyErrorHandler newInstance() {
        return new MyErrorHandler();
    }

    /**
     * Receive notification of a recoverable error.
     * @param e a recoverable parser exception error.
     */
    @Override
    public void error(SAXParseException e) {
        errorOccured = true;
    }

    /**
     * Receive notification of a parser warning.
     * @param e a parser warning  event.
     */
    @Override
    public void warning(SAXParseException e) {
        errorOccured = true;
    }

    /**
     * Report a fatal XML parsing error.
     * @param e The error information encoded as an exception.
     */
    @Override
    public void fatalError(SAXParseException e) {
        errorOccured = true;
    }

    /**
     * Has any event been received.
     *
     * @return true if any event has been received.
     *         false if no event has been received.
     */
    public boolean isErrorOccured() {
        return errorOccured;
    }
}
