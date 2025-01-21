/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 6956758
 *
 * @summary  NPE in com.sun.tools.javac.code.Symbol - isSubClass
 * @author Maurizio Cimadamore
 * @compile T6956758pos.java
 *
 */

class T6956758pos {

    interface I {}

    static class C {
        <T extends C & I> T cloneObject(T object) throws Exception {
            return (T)object.clone();
        }
    }
}
