/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterAbortException;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

/*
 * @test
 * @bug 4245280 8335231
 * @key printer
 * @summary PrinterJob not cancelled when PrinterJob.cancel() is used
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual PrinterJobCancel
 */
public class PrinterJobCancel extends Thread implements Printable {
    private final PrinterJob pj;
    private final boolean okayed;
    private static final String INSTRUCTIONS =
            "Test that print job cancellation works.\n\n" +
            "This test starts after clicking OK / Print button.\n" +
            "While the print job is in progress, the test automatically cancels it.\n" +
            "The test will complete automatically.";

    public static void main(String[] args) throws Exception {
        if (PrinterJob.lookupPrintServices().length == 0) {
            throw new RuntimeException("Printer not configured or available.");
        }

        PassFailJFrame passFailJFrame = PassFailJFrame.builder()
                .instructions(INSTRUCTIONS)
                .rows((int) INSTRUCTIONS.lines().count() + 1)
                .columns(45)
                .build();

        PrinterJobCancel pjc = new PrinterJobCancel();
        if (pjc.okayed) {
            pjc.start();
            Thread.sleep(5000);
            pjc.pj.cancel();
        } else {
            PassFailJFrame.forceFail("User cancelled printing");
        }
        passFailJFrame.awaitAndCheck();
    }

    public PrinterJobCancel() {
        pj = PrinterJob.getPrinterJob();
        pj.setPrintable(this);
        okayed = pj.printDialog();
    }

    public void run() {
        boolean cancelWorked = false;
        try {
            pj.print();
        } catch (PrinterAbortException paex) {
            cancelWorked = true;
            System.out.println("Job was properly cancelled and we");
            System.out.println("got the expected PrintAbortException");
            PassFailJFrame.forcePass();
        } catch (PrinterException prex) {
            prex.printStackTrace();
            PassFailJFrame.forceFail("Unexpected PrinterException caught:" + prex.getMessage());
        } finally {
            System.out.println("DONE PRINTING");
            if (!cancelWorked) {
                PassFailJFrame.forceFail("Didn't get the expected PrintAbortException");
            }
        }
    }

    @Override
    public int print(Graphics g, PageFormat pagef, int pidx) {
        if (pidx > 5) {
            return (Printable.NO_SUCH_PAGE);
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pagef.getImageableX(), pagef.getImageableY());
        g2d.setColor(Color.black);
        g2d.drawString(("This is page" + (pidx + 1)), 60, 80);
        // Need to slow things down a bit .. important not to try this
        // on the event dispatching thread of course.
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {
        }

        return Printable.PAGE_EXISTS;
    }
}
