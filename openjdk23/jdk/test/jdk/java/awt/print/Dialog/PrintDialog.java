/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
  @test
  @bug 6342748
  @key printer
  @summary  Pass if dialogs display correctly
  @run main/manual PrintDialog
*/
import java.awt.print.*;
import javax.print.attribute.*;

public class PrintDialog {

    public static void main(java.lang.String[] args) {
        PrinterJob pj = PrinterJob.getPrinterJob();
        PrintRequestAttributeSet pSet = new HashPrintRequestAttributeSet();
        System.out.println("Verify page setup dialog appears correctly then cancel or OK");
        pj.pageDialog(pSet);
        System.out.println("Verify all tabs of print dialog appear correctly then cancel or OK");
        pj.printDialog(pSet);
        return;
     }
}
