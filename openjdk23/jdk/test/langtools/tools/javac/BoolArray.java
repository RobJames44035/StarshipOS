/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4832887
 * @summary NPE from com.sun.tools.javac.v8.comp.Flow.checkInit in Mantis beta
 * @author gafter
 *
 * @compile BoolArray.java
 */

class BoolArray {
    static {
        boolean x=false, y=true;
        boolean[] a = new boolean[] {!x, y};
    }
}
