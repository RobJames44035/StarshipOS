/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
  @test
  @bug 6194489
  @summary tests that removeFlavorListener does not throw an exception in any case.
  @key headful
  @run main RemoveFlavorListenerTest
*/

import java.awt.Toolkit;
import java.awt.datatransfer.FlavorEvent;
import java.awt.datatransfer.FlavorListener;

public class RemoveFlavorListenerTest {

    public static void main(String[] args) {
        try {
            FlavorListener fl = new FlavorListener() {
                public void flavorsChanged(FlavorEvent e) {}
            };
            Toolkit.getDefaultToolkit()
                    .getSystemClipboard().removeFlavorListener(fl);
        } catch (NullPointerException e) {
            throw new RuntimeException("NullPointerException, test case failed",
                    e);
        }
    }
}
