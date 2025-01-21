/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */


/*
 * @test
 * @key headful
 * @bug 8054359
 *
 * @summary Check whether FileDialog blocks a non-modal Dialog
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
 * @run main FileDialogNonModal2Test
 */

public class FileDialogNonModal2Test {

    public static void main(String[] args) throws Exception {
        (new FileDialogFWDTest(null,
            FileDialogFWDTest.DialogOwner.NULL_DIALOG)).doTest();
    }
}
