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
 * Represents an byte (i.e. a byte field in an instance).
 *
 * @author      Bill Foote
 */


public class JavaByte extends JavaValue {

    byte value;

    public JavaByte(byte value) {
        this.value = value;
    }

    public String toString() {
        return "0x" + Integer.toString(((int) value) & 0xff, 16);
    }

}
