/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

// Part of test ../ProtecteInner.java

package pkg2;

public class Sub extends pkg1.Base {
    private class Inner {
        public void run() {
            baseMethod();
            Sub.this.baseMethod();
        }
    }
}
