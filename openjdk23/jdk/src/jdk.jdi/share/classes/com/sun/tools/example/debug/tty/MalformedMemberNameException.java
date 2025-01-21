/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/*
 * This source code is provided to illustrate the usage of a given feature
 * or technique and has been deliberately simplified. Additional steps
 * required for a production-quality application, such as security checks,
 * input validation and proper error handling, might not be present in
 * this sample code.
 */


package com.sun.tools.example.debug.tty;

class MalformedMemberNameException extends Exception {
    private static final long serialVersionUID = 7759071468833196630L;

    public MalformedMemberNameException() {
        super();
    }

    public MalformedMemberNameException(String s) {
        super(s);
    }
}
