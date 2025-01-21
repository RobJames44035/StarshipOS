/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.Dialog;

/*
 * @test
 * @key headful
 * @bug 8054143
 * @summary Check whether an application modal dialog having a hidden Frame
 *          constructor still stays on top of the blocked windows even
 *          after calling toBack for the dialog.
 *
 * @library ../helpers /lib/client/
 * @library /test/lib
 * @build ExtendedRobot
 * @build Flag
 * @build TestDialog
 * @build TestFrame
 * @run main ToBackAppModal3Test
 */

public class ToBackAppModal3Test {

    public static void main(String[] args) throws Exception {
        (new ToBackFDFTest(Dialog.ModalityType.APPLICATION_MODAL,
            ToBackFDFTest.DialogOwner.HIDDEN_FRAME)).doTest();
    }
}
