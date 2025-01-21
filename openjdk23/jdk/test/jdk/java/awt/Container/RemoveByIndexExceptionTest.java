/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
  @test
  @bug 4546535
  @summary java.awt.Container.remove(int) throws unexpected NPE
*/

import java.awt.Canvas;
import java.awt.Panel;

public class RemoveByIndexExceptionTest {

    public static void main(String[] args) throws Exception {
        Panel p = new Panel();
        p.add(new Canvas());
        p.remove(0);

        int[] bad = {-1, 0, 1};
        for (int i = 0; i < bad.length; i++) {
            try {
                System.out.println("Removing " + bad[i]);
                p.remove(bad[i]);
                System.out.println("No exception");
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
                System.out.println("This is correct exception - " + e);
            } catch (NullPointerException e) {
                e.printStackTrace();
                throw new RuntimeException("Test Failed: NPE was thrown.");
            }
        }
        System.out.println("Test Passed.");
    }
}
