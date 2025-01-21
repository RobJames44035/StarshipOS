/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

package two;

interface I {
    int i = 11;
}

public class Child extends one.Parent implements I {
    void method() {
        System.out.println(i);
    }
}
