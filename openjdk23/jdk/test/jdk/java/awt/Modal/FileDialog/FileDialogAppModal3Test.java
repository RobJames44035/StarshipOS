/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.Dialog;

/*
 * @test
 * @key headful
 * @bug 8054359 7186009
 *
 * @summary Check whether FileDialog blocks an application modal Dialog
 *          created with a hidden Frame constructor. Also check if other
 *          windows are blocked by the FileDialog too.
 *
 * @library ../helpers /lib/client/
 * @library /test/lib
 * @build ExtendedRobot
 * @build Flag
 * @build TestDialog
 * @build TestFrame
 * @build TestWindow
 * @run main FileDialogAppModal3Test
 */

public class FileDialogAppModal3Test {

    public static void main(String[] args) throws Exception {
        (new FileDialogFWDTest(Dialog.ModalityType.APPLICATION_MODAL,
            FileDialogFWDTest.DialogOwner.HIDDEN_FRAME)).doTest();
    }
}
