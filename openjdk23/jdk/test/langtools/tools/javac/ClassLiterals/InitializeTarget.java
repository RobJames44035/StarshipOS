/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4468823
 * @summary class literal causes the referenced class to be initialized
 * @author gafter
 *
 * @compile InitializeTarget.java
 * @run main InitializeTarget
 */

public class InitializeTarget {
    public static void main(String[] args) {
        A.class.toString();
    }
}

class A {
    static {
        if (true) throw new Error();
    }
}
