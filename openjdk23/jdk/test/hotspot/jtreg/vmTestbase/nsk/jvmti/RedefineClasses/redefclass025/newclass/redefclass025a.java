/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.RedefineClasses;

class redefclass025a {
    int intValue;

    public redefclass025a() {
        intValue = 0;
    }

    final void setValue(int i) {
        intValue = i;
    }

    synchronized int getValue() {
        return intValue;
    }
}
