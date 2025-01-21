/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @key printer
 * @bug 8048328
 * @summary CUPS Printing does not report supported printer resolutions.
 * @run main PrintResAttr
 */

/*
 * Since there is no guarantee you have any printers that support
 * resolution this test can't verify that resolution is being
 * reported when supported. But when they are it should test that
 * the code behaves reasonably.
 */
import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;

public class PrintResAttr {

   public static void main(String args[]) throws Exception {

      PrintService[] services =
            PrintServiceLookup.lookupPrintServices(null,null);
      for (int i=0; i<services.length; i++) {
          if (services[i].isAttributeCategorySupported(PrinterResolution.class)) {
              System.out.println("Testing " + services[i]);
              PrinterResolution[] res = (PrinterResolution[])
            services[i].getSupportedAttributeValues(PrinterResolution.class,
                                                      null,null);
              System.out.println("# supp res= " + res.length);
              for (int r=0;r<res.length;r++) System.out.println(res[r]);
          }
      }
   }
}
