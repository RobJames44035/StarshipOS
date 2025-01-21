/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

package com.sun.tools.classfile;

/*
 *  <p><b>This is NOT part of any supported API.
 *  If you write code that depends on this, you do so at your own risk.
 *  This code and its internal interfaces are subject to change or
 *  deletion without notice.</b>
 */
public class ConstantPoolException extends Exception {
    private static final long serialVersionUID = -2324397349644754565L;
    ConstantPoolException(int index) {
        this.index = index;
    }

    public final int index;
}
