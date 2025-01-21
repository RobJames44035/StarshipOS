/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xalan.internal.xsltc.compiler;

/**
 * @author Morten Jorgensen
 */
public final class CompilerException extends Exception {
    static final long serialVersionUID = 1732939618562742663L;

    private String _msg;

    public CompilerException() {
        super();
    }

    public CompilerException(Exception e) {
        super(e.toString());
        _msg = e.toString();
    }

    public CompilerException(String message) {
        super(message);
        _msg = message;
    }

    public String getMessage() {
        final int col = _msg.indexOf(':');

        if (col > -1)
            return(_msg.substring(col));
        else
            return(_msg);
    }
}
