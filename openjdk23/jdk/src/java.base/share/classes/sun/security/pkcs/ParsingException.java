/*
 * StarshipOS Copyright (c) 1996-2025. R.A. James
 */

package sun.security.pkcs;

import java.io.IOException;

/**
 * Generic PKCS Parsing exception.
 *
 * @author Benjamin Renaud
 */
public class ParsingException extends IOException {

    @java.io.Serial
    private static final long serialVersionUID = -6316569918966181883L;

    public ParsingException() {
        super();
    }

    public ParsingException(String s) {
        super(s);
    }
}
