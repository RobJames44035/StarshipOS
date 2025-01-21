/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
  @test
  @bug 6334074 8022536
  @key printer
  @summary test supported text flavors reported properly
  @run main TextFlavorTest
*/

import javax.print.*;
import javax.print.attribute.standard.*;
import javax.print.attribute.*;
import java.io.*;

public class TextFlavorTest {

    public static void main(String[] args) throws Exception {

        PrintService service[] =
            PrintServiceLookup.lookupPrintServices(null, null);

        if (service.length == 0) {
            System.out.println("No print service found.");
            return;
        }

        for (int y = 0; y < service.length; y ++) {
            DocFlavor flavors[] = service[y].getSupportedDocFlavors();
            if (flavors == null) continue;
            for (int x = 0; x < flavors.length; x ++) {
                if (!service[y].isDocFlavorSupported(flavors[x])) {
                    String msg = "DocFlavor " + flavors[x] +
                        " is not supported by service "+ service[y];
                    throw new RuntimeException(msg);
                }
            }
        }
        System.out.println("Test passed.");
    }
}
