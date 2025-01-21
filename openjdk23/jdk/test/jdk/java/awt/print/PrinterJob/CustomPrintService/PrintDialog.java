/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

import java.awt.*;
import java.awt.print.PrinterJob;
import javax.print.PrintServiceLookup;

/**
 * @test
 * @bug 6870661
 * @summary Verify that no native dialog is opened for a custom PrintService
 * @run main/manual PrintDialog
 */
public class PrintDialog {

    private static final String instructions =
        "This test shows a non native print dialog having a 'test' print service\n" +
        "selected. No other options are selectable on the General tab. The other\n" +
        "tabs are as follows:\n" +
        "Page Setup: Media & Margins enabled, Orientation disabled\n" +
        "Appearance: All parts disabled\n\n" +
        "Test passes if the dialog is shown as described above.";

    public static void main(String[] args) throws Exception {
        // instruction dialog
        Frame instruction = new Frame("Verify that no native print dialog is showed");
        instruction.add(new TextArea(instructions));
        instruction.pack();
        instruction.show();
        // test begin
        PrintServiceStub service = new PrintServiceStub("test");
        PrintServiceLookup.registerService(service);
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintService(service);
        job.printDialog();
        System.out.println("test passed");
    }
}
