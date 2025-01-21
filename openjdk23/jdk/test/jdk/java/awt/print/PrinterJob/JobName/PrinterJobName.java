/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;

/*
 * @test
 * @bug 4205601
 * @summary setJobName should be used by PrinterJob
 * @key printer
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual PrinterJobName
 */
public class PrinterJobName implements Printable {
    private static final String THE_NAME = "Testing the Job name setting";

    private static final String INSTRUCTIONS =
            "This test prints a page with a banner/job name of\n\n" +
            THE_NAME;

    public static void main(String[] args) throws Exception {
        if (PrinterJob.lookupPrintServices().length == 0) {
            throw new RuntimeException("Printer not configured or available.");
        }

        PassFailJFrame passFailJFrame = PassFailJFrame.builder()
                .instructions(INSTRUCTIONS)
                .rows((int) INSTRUCTIONS.lines().count() + 1)
                .columns(45)
                .build();

        PrinterJob job = PrinterJob.getPrinterJob();
        job.setJobName(THE_NAME);
        job.setPrintable(new PrinterJobName());
        job.print();
        passFailJFrame.awaitAndCheck();
    }

    @Override
    public int print(Graphics g, PageFormat pgFmt, int pgIndex) {
        if (pgIndex > 0) {
            return Printable.NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pgFmt.getImageableX(), pgFmt.getImageableY());
        g2d.drawString("Name is: " + THE_NAME, 20, 20);
        return Printable.PAGE_EXISTS;
    }
}
