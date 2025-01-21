/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */


/*
 * The Original Code is HAT. The Initial Developer of the
 * Original Code is Bill Foote, with contributions from others
 * at JavaSoft/Sun.
 */

package jdk.test.lib.hprof.model;

/**
 * This is used to represent values that the program doesn't really understand.
 * This includes the null vlaue, and unresolved references (which shouldn't
 * happen in well-formed hprof files).
 *
 *
 * @author      Bill Foote
 */




public class HackJavaValue extends JavaValue {

    private String value;
    private long size;

    public HackJavaValue(String value, long size) {
        this.value = value;
        this.size = size;
    }

    public String toString() {
        return value;
    }

    @Override
    public long getSize() {
        return size;
    }

}
