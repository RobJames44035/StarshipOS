/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.Dialog;

/*
 * @test
 * @key headful
 * @bug 8052012
 * @summary Check whether an application modal Dialog created with hidden Dialog
 *          constructor stays on top of the windows it blocks.
 *
 * @library ../helpers /lib/client/
 * @library /test/lib
 * @build ExtendedRobot
 * @build Flag
 * @build TestDialog
 * @build TestFrame
 * @run main OnTopAppModal4Test
 */

public class OnTopAppModal4Test {

    public static void main(String[] args) throws Exception {
        (new OnTopFDFTest(
            Dialog.ModalityType.APPLICATION_MODAL,
            OnTopFDFTest.DialogOwner.HIDDEN_DIALOG)).doTest();
    }
}
