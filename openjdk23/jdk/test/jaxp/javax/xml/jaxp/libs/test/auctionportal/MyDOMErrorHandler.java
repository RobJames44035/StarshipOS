/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */
package test.auctionportal;

import org.w3c.dom.DOMErrorHandler;
import org.w3c.dom.DOMError;

/**
 * Error handler for recording DOM processing error.
 */
public class MyDOMErrorHandler implements DOMErrorHandler {
    /**
     * flag shows if there is any error.
     */
    private volatile boolean errorOccured = false;

    /**
     * Set errorOcurred to true when an error occurs.
     * @param error The error object that describes the error. This object
     * may be reused by the DOM implementation across multiple calls to
     * the handleError method.
     * @return true that processing may continue depending on.
     */
    @Override
    public boolean handleError (DOMError error) {
        System.err.println( "ERROR" + error.getMessage());
        System.err.println( "ERROR" + error.getRelatedData());
        errorOccured = true;
        return true;
    }

    /**
     * Showing if any error was handled.
     * @return true if there is one or more error.
     *         false no error occurs.
     */
    public boolean isError() {
        return errorOccured;
    }
}
