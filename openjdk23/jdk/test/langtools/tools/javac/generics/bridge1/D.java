/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4821489
 * @summary generics: missing bridge method
 * @author gafter
 *
 * @compile  A.java C.java D.java E.java
 * @run main D
 */

// the main class
import java.io.*;

public class D extends C {
    public D() {
        super();
    }

    public D test() {
        System.out.println("D called");
        return new D();
    }

    public static void main(String[] agrs) {
        A obj = new D();
        obj.test();
    }
}
