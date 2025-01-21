/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/**
 * @test
 * @bug 6446094
 * @key printer
 * @summary Don't re-create print services.
 * @run main SameService
 */

import java.awt.*;
import java.awt.print.*;
import javax.print.*;

public class SameService implements Printable {

    public static void main(String args[]) throws Exception {
        PrinterJob job1 = PrinterJob.getPrinterJob();
        job1.setPrintable(new SameService());
        PrintService service1 = job1.getPrintService();
        PrinterJob job2 = PrinterJob.getPrinterJob();
        job2.setPrintable(new SameService());
        PrintService service2 = job2.getPrintService();

        if (service1 != service2) {
           throw new RuntimeException("Duplicate service created");
        }
    }

     public int print(Graphics g, PageFormat pf, int pi)
                       throws PrinterException  {
          return NO_SUCH_PAGE;
     }

}
