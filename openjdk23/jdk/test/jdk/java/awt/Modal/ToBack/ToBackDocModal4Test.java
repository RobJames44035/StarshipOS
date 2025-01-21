/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.Dialog;

/*
 * @test
 * @key headful
 * @bug 8054143
 * @summary Check if toBack method works correctly for
 *          a document modal dialog with hidden Dialog parent.
 *
 * @library ../helpers /lib/client/
 * @library /test/lib
 * @build ExtendedRobot
 * @build Flag
 * @build TestDialog
 * @build TestFrame
 * @run main ToBackDocModal4Test
 */

public class ToBackDocModal4Test {

    public static void main(String[] args) throws Exception {
        (new ToBackFDFTest(Dialog.ModalityType.DOCUMENT_MODAL,
            ToBackFDFTest.DialogOwner.HIDDEN_DIALOG)).doTest();
    }
}
