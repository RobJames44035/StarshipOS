/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.Dialog;

/*
 * @test
 * @key headful
 * @bug 8049617
 * @summary Check whether a modeless Dialog created with a Dialog
 *          constructor receives focus, whether its components receive focus
 *          and respond to key events. Check also if the other windows
 *          receive mouse and key events.
 *
 * @library ../helpers /lib/client/
 * @library /test/lib
 * @build ExtendedRobot
 * @build Flag
 * @build TestDialog
 * @build TestFrame
 * @build TestWindow
 * @run main BlockingDFWModeless2Test
 */

public class BlockingDFWModeless2Test {

    public static void main(String[] args) throws Exception {
        (new BlockingDFWTest(
            BlockingDFWTest.Parent.DIALOG,
            Dialog.ModalityType.MODELESS)).doTest();
    }
}
