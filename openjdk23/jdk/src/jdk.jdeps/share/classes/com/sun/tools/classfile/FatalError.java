/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package com.sun.tools.classfile;

/**
 *  <p><b>This is NOT part of any supported API.
 *  If you write code that depends on this, you do so at your own risk.
 *  This code and its internal interfaces are subject to change or
 *  deletion without notice.</b>
 */
public class FatalError extends Error {
    private static final long serialVersionUID = 8114054446416187030L;

    FatalError(String message) {
        super(message);
    }
}
