/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @summary class p3.c3 defined in a named package in an unnamed module tries to access c4
 *          defined in an unnamed package in an unnamed module.  Access allowed since
 *          any class in an unnamed module can read an unnamed module.
 * @library /test/lib
 * @compile myloaders/MyDiffClassLoader.java
 * @compile c4.java
 * @compile p3/c3.jcod
 * @run main/othervm -Xbootclasspath/a:. UmodDiffCL_UmodUpkg
 */

import myloaders.MyDiffClassLoader;

public class UmodDiffCL_UmodUpkg {

    public void testAccess() throws Throwable {

        Class p3_c3_class = MyDiffClassLoader.loader1.loadClass("p3.c3");
        try {
            p3_c3_class.newInstance();
        } catch (IllegalAccessError e) {
          System.out.println(e.getMessage());
              throw new RuntimeException("Test Failed, public type c3 defined in an unnamed module " +
                                         "should be able to access public type c4 defined in an unnamed module");
        }
    }

    public static void main(String args[]) throws Throwable {
      UmodDiffCL_UmodUpkg test = new UmodDiffCL_UmodUpkg();
      test.testAccess();
    }
}
