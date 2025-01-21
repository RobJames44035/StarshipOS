/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
  @test
  @bug 4378378
  @summary Tests that checkbox.setLabel(null) doesn't crash the VM.
  @key headful
*/

import java.awt.Checkbox;
import java.awt.EventQueue;
import java.awt.Frame;

public class CheckboxCrashTest  {

    static Frame f;

    public static void main(String[] args) throws Exception {
        try {
            EventQueue.invokeAndWait(() -> runTest());
            Thread.sleep(1000);
        } finally {
           f.dispose();
        }
    }

    static void runTest() {
        f = new Frame("CheckboxCrashTest");
        Checkbox cb = new Checkbox();
        f.add(cb);
        f.pack();
        cb.setLabel(null);
        f.setVisible(true);
    }
}
