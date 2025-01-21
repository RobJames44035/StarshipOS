/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
  @test
  @bug 4513402
  @summary AWTEventMulticaster.getListeners throws unexpected ArrayStoreException
*/

import java.awt.AWTEventMulticaster;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentListener;
import java.awt.event.FocusListener;

public class ArrayStoreException {

    public static void main(String[] args) throws Exception {

        ComponentListener mc =
            AWTEventMulticaster.add(
                new ComponentAdapter() {},
                new ComponentAdapter() {});

        if (AWTEventMulticaster.getListeners(mc, FocusListener.class).length == 0) {
            System.out.println("OKAY");
        } else {
            System.out.println("empty array expected");
            throw new RuntimeException("Test failed");
        }
    }
}
