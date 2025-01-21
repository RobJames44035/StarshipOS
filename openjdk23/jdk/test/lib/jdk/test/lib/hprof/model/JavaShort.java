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
 * Represents a short (i.e. a short field in an instance).
 *
 * @author      Bill Foote
 */


public class JavaShort extends JavaValue {

    short value;

    public JavaShort(short value) {
        this.value = value;
    }

    public String toString() {
        return "" + value;
    }

}
