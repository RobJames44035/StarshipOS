/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
  @test
  @bug 4259272
  @summary tests that notifications on changes to the set of DataFlavors
           available on a private clipboard are delivered properly
  @build Common
  @run main PrivateClipboardTest
*/

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class PrivateClipboardTest {

    public static void main(String[] args) {
        new PrivateClipboardTest().start();
    }

    public void start() {
        final Clipboard clipboard = new Clipboard("local");

        final FlavorListenerImpl listener1 = new FlavorListenerImpl();
        clipboard.addFlavorListener(listener1);

        final FlavorListenerImpl listener2 = new FlavorListenerImpl();
        clipboard.addFlavorListener(listener2);

        Util.setClipboardContents(clipboard,
                new StringSelection("text1"), null);
        Util.sleep(3000);

        clipboard.removeFlavorListener(listener1);

        Util.setClipboardContents(clipboard,
                new TransferableUnion(new StringSelection("text2"),
                        new ImageSelection(Util.createImage())), null);
        Util.sleep(3000);

        System.err.println("listener1: " + listener1 + "\nlistener2: " + listener2);

        if (!(listener1.notified1 && listener2.notified1 && !listener1.notified2
                && listener2.notified2)) {
            throw new RuntimeException("notifications about flavor " +
                                       "changes delivered incorrectly!");
        }
     }
}
