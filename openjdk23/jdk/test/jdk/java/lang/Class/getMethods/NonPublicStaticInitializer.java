/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/* @test
   @bug 4187388
   @summary <clinit> in interfaces need not be public
*/

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

interface TestedInterface {
    String s = System.getProperty("Test");

    void foo();
    void bar();
}


public class NonPublicStaticInitializer {
    public static void main(String args[]) throws Exception {
        Method m[] = TestedInterface.class.getMethods();
        for (int i = 0; i < m.length; i++) {
            System.out.println("Found: " +
                               Modifier.toString(m[i].getModifiers()) +
                               " " + m[i].getName());
            if (m[i].getName().equals("<clinit>")) {
                throw new Exception("Shouldn't have found <clinit>");
            }
        }
    }
}
