/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/**
 * @test
 * @bug 4694495
 * @key printer
 * @summary Check that the dialog shows copies = 3.
 * @run main/manual Test
 */
import java.awt.print.*;
import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;

public class Test {
        static public void main(String args[]) {
                DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PAGEABLE;
                PrintRequestAttributeSet aSet
                    = new HashPrintRequestAttributeSet();
                PrintService[] services
                    = PrintServiceLookup.lookupPrintServices(flavor, aSet);

                PrinterJob pj = PrinterJob.getPrinterJob();

                for (int i=0; i<services.length; i++)
                    System.out.println(services[i].getName());
                try { pj.setPrintService(services[services.length-1]); }
                catch (Exception e) { e.printStackTrace(); }
                pj.setCopies(3);
                pj.printDialog();
        }
}
