/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4705305
 * @summary javac generates incorrect class_index for static ref by simple name
 * @author gafter
 *
 * @compile T1.java T3.java
 * @compile T2.java
 * @run main T3
 */

class T1 {
    static void f() { throw new Error(); }
    static boolean ok = false;
}

class T2 extends T1 {
}
