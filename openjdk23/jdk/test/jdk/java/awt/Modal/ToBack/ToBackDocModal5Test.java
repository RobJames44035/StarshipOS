/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.Dialog;

/*
 * @test
 * @key headful
 * @bug 8054143
 * @summary Check whether a document modal dialog having a visible Frame
 *          constructor still stays on top of the blocked windows even
 *          after calling toBack for the dialog.
 *
 * @library ../helpers /lib/client/
 * @library /test/lib
 * @build ExtendedRobot
 * @build Flag
 * @build TestDialog
 * @build TestFrame
 * @run main ToBackDocModal5Test
 */

public class ToBackDocModal5Test {

    public static void main(String[] args) throws Exception {
        (new ToBackFDFTest(Dialog.ModalityType.DOCUMENT_MODAL,
            ToBackFDFTest.DialogOwner.FRAME)).doTest();
    }
}
