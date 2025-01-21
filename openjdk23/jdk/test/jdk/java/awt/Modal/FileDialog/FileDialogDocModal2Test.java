/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.Dialog;

/*
 * @test
 * @key headful
 * @bug 8054359 7186009
 *
 * @summary Check whether FileDialog blocks a document modal Dialog
 *          created with a null Dialog constructor. Also check if other
 *          windows are blocked by the FileDialog too.
 *
 * @library ../helpers /lib/client/
 * @library /test/lib
 * @build ExtendedRobot
 * @build Flag
 * @build TestDialog
 * @build TestFrame
 * @build TestWindow
 * @run main FileDialogDocModal2Test
 */

public class FileDialogDocModal2Test {

    public static void main(String[] args) throws Exception {
        (new FileDialogFWDTest(Dialog.ModalityType.DOCUMENT_MODAL,
            FileDialogFWDTest.DialogOwner.NULL_DIALOG)).doTest();
    }
}
