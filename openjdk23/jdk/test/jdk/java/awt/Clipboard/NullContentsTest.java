/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
  @test
  @bug 4378007 4250859
  @summary Verifies that setting the contents of the system Clipboard to null
           throws a NullPointerException
  @key headful
  @run main NullContentsTest
*/

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class NullContentsTest {

    public static void main(String[] args) {
        // Clipboard.setContents(null, foo) should throw an NPE, but
        // Clipboard.setContents(bar, foo), where bar.getTransferData(baz)
        // returns null, should not.
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        try {
            clip.setContents(null, null);
        } catch (NullPointerException e) {
            StringSelection ss = new StringSelection(null);
            try {
                clip.setContents(ss, null);
            } catch (NullPointerException ee) {
                throw new RuntimeException("test failed: null transfer data");
            }
            System.err.println("test passed");
            return;
        }
        throw new RuntimeException("test failed: null Transferable");
    }
}
