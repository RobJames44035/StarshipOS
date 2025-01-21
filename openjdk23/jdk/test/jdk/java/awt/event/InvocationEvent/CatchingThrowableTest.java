/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
  @test
  @bug 4403712
  @summary Error thrown in InvokeAndWait runnable causes hang
  @run main CatchingThrowableTest
*/

import java.awt.EventQueue;

public class CatchingThrowableTest {
    public static void main(String args[]) {
        try {
            EventQueue.invokeAndWait(() -> {
                throw new RuntimeException("My Error");
            });
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (java.lang.reflect.InvocationTargetException ex) {
            ex.printStackTrace();
        }

        System.err.println("Test passed.");
    }
}
