/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.Dialog;

/*
 * @test
 * @key headful
 * @bug 8052012
 * @summary Check whether a toolkit modal Dialog created with visible Frame
 *          constructor stays on top of the windows it blocks.
 *
 * @library ../helpers /lib/client/
 * @library /test/lib
 * @build ExtendedRobot
 * @build Flag
 * @build TestDialog
 * @build TestFrame
 * @run main OnTopTKModal5Test
 */

public class OnTopTKModal5Test {

    public static void main(String[] args) throws Exception {
        (new OnTopFDFTest(
            Dialog.ModalityType.TOOLKIT_MODAL,
            OnTopFDFTest.DialogOwner.FRAME)).doTest();
    }
}
