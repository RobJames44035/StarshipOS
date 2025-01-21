/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.Dialog;

/*
 * @test
 * @key headful
 * @bug 7125054 8044429
 * @summary Check whether a printDialog blocks an application modality excluded Frame
 *          (it shouldn't). Checks also whether setting a parent frame to be
 *          modality excluded excludes its children from being blocked too.
 *
 * @library ../helpers /lib/client/
 * @library /test/lib
 * @build ExtendedRobot
 * @build Flag
 * @build TestDialog
 * @build TestFrame
 * @build TestWindow
 * @run main ApplicationExcludeFramePrintSetupTest
 */

public class ApplicationExcludeFramePrintSetupTest {

    public static void main(String[] args) throws Exception {
        ExcludeFrameTest test = new ExcludeFrameTest(
                Dialog.ModalExclusionType.APPLICATION_EXCLUDE,
                ExcludeFrameTest.DialogToShow.PRINT_SETUP);
        test.doTest();
    }
}
