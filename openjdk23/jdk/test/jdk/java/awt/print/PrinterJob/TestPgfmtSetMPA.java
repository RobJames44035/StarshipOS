/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */
/*
 * @test
 * @bug 6789262
 * @key printer
 * @summary  Verifies if getPageFormat returns correct mediaprintable value
 * @run main TestPgfmtSetMPA
 */
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;

public class TestPgfmtSetMPA {

    public static void main(String args[]) {
        PrinterJob job;

        job = PrinterJob.getPrinterJob();
        if (job.getPrintService() == null) {
            System.out.println("No printers. Test cannot continue");
            return;
        }

        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

        // Here you could see the PageFormat with an empty PrintRequestAttributeSet
        PageFormat pf2 = job.getPageFormat(pras);
        System.out.println((pf2.getImageableX() / 72f) + " "
                + (pf2.getImageableY() / 72f) + " "
                + (pf2.getImageableWidth() / 72f) + " "
                + (pf2.getImageableHeight() / 72f)
        );

        // Set left margin to 2.0
        pras.add(new MediaPrintableArea(2.0f,
                (float)(pf2.getImageableY() / 72f),
                (float) ((pf2.getImageableWidth() / 72f) - 1.0f),
                (float) (pf2.getImageableHeight() / 72f),
                MediaPrintableArea.INCH));

        pf2 = job.getPageFormat(pras);
        System.out.println((pf2.getImageableX() / 72f) + " "
                + (pf2.getImageableY() / 72f) + " "
                + (pf2.getImageableWidth() / 72f) + " "
                + (pf2.getImageableHeight() / 72f)
        );

        // check if returned left margin of imageable area is 2.0 as set earlier
        if (pf2.getImageableX() / 72f != 2.0f) {
            throw new RuntimeException("getPageFormat doesn't apply specified "
                    + "MediaPrintableArea attribute");
        }
    }
}
