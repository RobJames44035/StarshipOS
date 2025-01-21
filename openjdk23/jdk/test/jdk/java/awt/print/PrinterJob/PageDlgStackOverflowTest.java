/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
import java.awt.print.PrinterJob;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.DialogTypeSelection;

/**
 * @test
 * @bug 8039412
 * @key printer
 * @run main/manual PageDlgStackOverflowTest
 * @summary Calling pageDialog() after printDialog with
 *          DialogTypeSelection.NATIVE should not result in StackOverflowError
 */
public class PageDlgStackOverflowTest {

    public static void main(String args[]) {
        PrinterJob job = PrinterJob.getPrinterJob();
        if (job == null) {
            return;
        }
        PrintRequestAttributeSet pSet =
             new HashPrintRequestAttributeSet();
        pSet.add(DialogTypeSelection.NATIVE);
        job.printDialog(pSet);
        try {
            job.pageDialog(pSet);
        } catch (StackOverflowError e) {
            throw new RuntimeException("StackOverflowError is thrown");
        }
    }
}

