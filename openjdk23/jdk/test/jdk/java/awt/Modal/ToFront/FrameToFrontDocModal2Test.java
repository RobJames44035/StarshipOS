/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.Dialog;

/*
 * @test
 * @key headful
 * @bug 8050885
 * @summary Check if toFront method works correctly for a document modal dialog.
 *
 * @library ../helpers /lib/client/
 * @library /test/lib
 * @build ExtendedRobot
 * @build Flag
 * @build TestDialog
 * @build TestFrame
 * @run main FrameToFrontDocModal2Test
 */

public class FrameToFrontDocModal2Test {

    public static void main(String[] args) throws Exception {

        for (FrameToFrontModalBlockedTest.DialogOwner o:
                FrameToFrontModalBlockedTest.DialogOwner.values()) {

            if (o != FrameToFrontModalBlockedTest.DialogOwner.FRAME) {
                (new FrameToFrontModalBlockedTest(
                    Dialog.ModalityType.DOCUMENT_MODAL, o)).doTest();
            }
        }

    }
}
