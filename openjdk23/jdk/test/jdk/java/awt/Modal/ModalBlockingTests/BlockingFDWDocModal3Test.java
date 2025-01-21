/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.Dialog;

/*
 * @test
 * @key headful
 * @bug 8049617
 * @summary Check whether a document modal Dialog created with a hidden Frame
 *          constructor receives focus; whether its components receive focus
 *          and respond to key events, when there are other windows shown.
 *          Check also the correctness of blocking behavior for other windows shown.
 *
 * @library ../helpers /lib/client/
 * @library /test/lib
 * @build ExtendedRobot
 * @build Flag
 * @build TestDialog
 * @build TestFrame
 * @build TestWindow
 * @run main BlockingFDWDocModal3Test
 */

public class BlockingFDWDocModal3Test {

    public static void main(String[] args) throws Exception {
        (new BlockingFDWTest(Dialog.ModalityType.DOCUMENT_MODAL,
            BlockingFDWTest.DialogOwner.HIDDEN_FRAME)).doTest();
    }
}
