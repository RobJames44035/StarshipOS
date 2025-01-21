/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.Dialog;

/*
 * @test
 * @key headful
 * @bug 8052012
 * @summary Check whether a document modal Dialog created with hidden Frame
 *          constructor follows normal Z order.
 *
 * @library ../helpers /lib/client/
 * @library /test/lib
 * @build ExtendedRobot
 * @build Flag
 * @build TestDialog
 * @build TestFrame
 * @run main OnTopDocModal3Test
 */

public class OnTopDocModal3Test {

    public static void main(String[] args) throws Exception {
        (new OnTopFDFTest(
            Dialog.ModalityType.DOCUMENT_MODAL,
            OnTopFDFTest.DialogOwner.HIDDEN_FRAME)).doTest();
    }
}
