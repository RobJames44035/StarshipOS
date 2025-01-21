/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.Dialog;

/*
 * @test
 * @key headful
 * @bug 8054359 7186009
 * @summary Check whether a FileDialog set to document modality behaves as expected.
 *
 * @library ../helpers /lib/client/
 * @library /test/lib
 * @build ExtendedRobot
 * @build Flag
 * @build TestDialog
 * @build TestFrame
 * @build TestWindow
 * @run main FileDialogDocModal7Test
 */

public class FileDialogDocModal7Test {

    public static void main(String[] args) throws Exception {
        (new FileDialogModalityTest(Dialog.ModalityType.DOCUMENT_MODAL)).doTest();
    }
}
