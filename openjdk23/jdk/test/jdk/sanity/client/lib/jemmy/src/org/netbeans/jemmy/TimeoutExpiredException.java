/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy;

/**
 *
 * Exception is supposed to be used to notice that some waiting was expired.
 *
 * @author Alexandre Iline (alexandre.iline@oracle.com)
 */
public class TimeoutExpiredException extends JemmyException {

    private static final long serialVersionUID = 42L;

    /**
     * Constructor.
     *
     * @param description Waiting description.
     */
    public TimeoutExpiredException(String description) {
        super(description);
    }
}
