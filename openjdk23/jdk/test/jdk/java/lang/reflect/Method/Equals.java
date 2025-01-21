/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4913433
 * @summary Generic framework to test Method.equals.
 *
 * @clean A
 * @compile Equals.java
 * @run main Equals
 */

import java.lang.reflect.*;

class A {
    public Object m() { return this; }
}

public class Equals extends A {
    public Equals m() { return this; }

    public static void main(String [] args) {
        Equals e = new Equals();
        e.returnType();
    }

    private void returnType() {
        Class c = this.getClass();
        Method [] ma = c.getMethods();
        Method m0 = null, m1 = null;

        for (int i = 0; i < ma.length; i++) {
            if (ma[i].getName().equals("m")) {
                if (m0 == null) {
                    m0 = ma[i];
                    continue;
                } else {
                    m1 = ma[i];
                    break;
                }
            }
        }

        if (m0 == null || m1 == null)
            throw new RuntimeException("Can't find bridge methods");

        if (m0.equals(m1))
            throw new RuntimeException("Return types not compared");
        System.out.println("\"" + m0 + "\" and \"" + m1 + "\" are different");
    }
}
