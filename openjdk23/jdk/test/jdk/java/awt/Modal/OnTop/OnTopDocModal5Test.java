/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.Dialog;

/*
 * @test
 * @key headful
 * @bug 8052012
 * @summary Check whether a document modal Dialog created with visible Frame
 *          constructor stays on top of the windows it blocks.
 *
 * @library ../helpers /lib/client/
 * @library /test/lib
 * @build ExtendedRobot
 * @build Flag
 * @build TestDialog
 * @build TestFrame
 * @run main OnTopDocModal5Test
 */

public class OnTopDocModal5Test {

    public static void main(String[] args) throws Exception {
        (new OnTopFDFTest(
            Dialog.ModalityType.DOCUMENT_MODAL,
            OnTopFDFTest.DialogOwner.FRAME)).doTest();
    }
}
