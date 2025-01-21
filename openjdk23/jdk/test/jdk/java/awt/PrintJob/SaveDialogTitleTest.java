/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 4851363 8025988 8025990
 * @key printer
 * @summary Tests the save to file dialog has a title.
 * @run main/manual=yesno/othervm SaveDialogTitleTest
 */

import java.awt.*;

public class SaveDialogTitleTest {

    public static void main(String args[]) {

        System.out.print("Once the dialog appears, press OK and the ");
        System.out.print("Save to File dialog should appear and it ");
        System.out.println("must have a window title else the test fails.");
        System.out.println("To test 8025988: Range should be selected with pages 3 to 8.");
        System.out.println("To test 8025990: Paper should be Legal and in Landscape.");
        Toolkit tk = Toolkit.getDefaultToolkit();
        JobAttributes jobAttributes = new JobAttributes();
        jobAttributes.setDestination(JobAttributes.DestinationType.FILE);
        jobAttributes.setDefaultSelection(JobAttributes.DefaultSelectionType.RANGE);
        jobAttributes.setPageRanges(new int[][]{new int[]{3,8}});
        PageAttributes page = new PageAttributes();
        page.setMedia(PageAttributes.MediaType.LEGAL);
        page.setOrientationRequested(PageAttributes.
                                        OrientationRequestedType.LANDSCAPE);

        PrintJob printJob =
            tk.getPrintJob(new Frame(), "Save Title Test",
                           jobAttributes, page);
        if (printJob != null) { // in case user cancels.
          printJob.end();
        }
        System.exit(0); // safe because use 'othervm'
    }
}
