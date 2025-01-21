/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.Dialog;

/*
 * @test
 * @key headful
 * @bug 8054359 8055752
 *
 * @summary Check whether FileDialog blocks a toolkit modal Dialog
 *          created with a hidden Dialog constructor. Also check if other
 *          windows are blocked by the FileDialog too.
 *
 * @library ../helpers /lib/client/
 * @library /test/lib
 * @build ExtendedRobot
 * @build Flag
 * @build TestDialog
 * @build TestFrame
 * @build TestWindow
 * @run main FileDialogTKModal4Test
 */

public class FileDialogTKModal4Test {

    public static void main(String[] args) throws Exception {
        (new FileDialogFWDTest(Dialog.ModalityType.TOOLKIT_MODAL,
            FileDialogFWDTest.DialogOwner.HIDDEN_DIALOG)).doTest();
    }
}
