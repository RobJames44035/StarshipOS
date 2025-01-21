/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test
 * @bug 4482888
 * @summary javac produces unverifiable classfile compiling conditional operator
 * @author gafter
 *
 * @compile InconsistentStack.java
 * @run main InconsistentStack
 */

public class InconsistentStack {
    public static void main(String[] args) {
        f1();
        f2();
    }
    static void f1() {
        boolean b = true, c = false;
        if ((b || true) ? c : !c) ;
    }
    static void f2() {
        boolean b = true, c = false;
        if ((c && false) ? b : b) ;
    }
}
