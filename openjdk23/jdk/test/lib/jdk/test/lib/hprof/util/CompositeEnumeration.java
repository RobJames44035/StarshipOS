/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */


/*
 * The Original Code is HAT. The Initial Developer of the
 * Original Code is Bill Foote, with contributions from others
 * at JavaSoft/Sun.
 */

package jdk.test.lib.hprof.util;

import java.util.Enumeration;
import java.util.NoSuchElementException;
import jdk.test.lib.hprof.model.JavaHeapObject;

public class CompositeEnumeration implements Enumeration<JavaHeapObject> {
    Enumeration<JavaHeapObject> e1;
    Enumeration<JavaHeapObject> e2;

    public CompositeEnumeration(Enumeration<JavaHeapObject> e1, Enumeration<JavaHeapObject> e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    public boolean hasMoreElements() {
        return e1.hasMoreElements() || e2.hasMoreElements();
    }

    public JavaHeapObject nextElement() {
        if (e1.hasMoreElements()) {
            return e1.nextElement();
        }

        if (e2.hasMoreElements()) {
            return e2.nextElement();
        }

        throw new NoSuchElementException();
    }
}
