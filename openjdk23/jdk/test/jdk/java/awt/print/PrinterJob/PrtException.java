/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/**
 * @test
 * @key printer
 * @bug 4429544
 * @summary This test should not throw a printer exception. Test has been modified to correspond with the behavior of 1.5 and above.
 * @run main PrtException
 */

import java.awt.*;
import java.awt.print.*;
import javax.print.*;

public class PrtException implements Printable {
    PrinterJob pj;

    public PrtException() {

        try{
            PrintService[] svc;
            PrintService defService = PrintServiceLookup.lookupDefaultPrintService();
            if (defService == null) {
                svc = PrintServiceLookup.lookupPrintServices(DocFlavor.SERVICE_FORMATTED.PRINTABLE, null);
                if (svc.length == 0) {
                    throw new RuntimeException("Printer is required for this test.  TEST ABORTED");
                }
                defService = svc[0];
            }

            System.out.println("PrintService found : "+defService);
            pj = PrinterJob.getPrinterJob();
            pj.setPrintService(defService);
            //pj.setPrintable(this); // commenting this line should not result in PrinterException
            pj.print();
        } catch(PrinterException e ) {
            e.printStackTrace();
            throw new RuntimeException(" PrinterException should not be thrown. TEST FAILED");
        }
        System.out.println("TEST PASSED");
    }


    public int print(Graphics g,PageFormat pf,int pageIndex) {
        Graphics2D g2= (Graphics2D)g;
        if(pageIndex>=1){
            return Printable.NO_SUCH_PAGE;
        }
        g2.translate(pf.getImageableX(), pf.getImageableY());
        g2.setColor(Color.black);
        g2.drawString("Hello world.", 10, 10);

        return Printable.PAGE_EXISTS;
    }

    public static void main(String arg[]) {
        PrtException sp = new PrtException();
    }
}
