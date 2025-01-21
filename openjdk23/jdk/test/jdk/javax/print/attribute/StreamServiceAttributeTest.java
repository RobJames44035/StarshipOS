/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 4884570
 * @summary Attribute support reporting should be consistent
*/

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import javax.print.DocFlavor;
import javax.print.StreamPrintService;
import javax.print.StreamPrintServiceFactory;
import javax.print.attribute.Attribute;
import javax.print.attribute.standard.Chromaticity;
import javax.print.attribute.standard.Media;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.SheetCollate;
import javax.print.attribute.standard.Sides;

public class StreamServiceAttributeTest {

    private static boolean allSupported = true;
    private static Class[] attrClasses = {
         Chromaticity.class,
         Media.class,
         OrientationRequested.class,
         SheetCollate.class,
         Sides.class,
    };

    public static void main(String args[]) {

        StreamPrintServiceFactory[] fact =
          StreamPrintServiceFactory.lookupStreamPrintServiceFactories(
                null, null);

        if (fact.length == 0) {
            return;
        }
        OutputStream out = new ByteArrayOutputStream();
        StreamPrintService sps = fact[0].getPrintService(out);
        for (Class<? extends Attribute> ac : attrClasses) {
            test(sps, ac);
        }

        if (!allSupported) {
            throw new RuntimeException("Inconsistent support reported");
        }
    }

    private static void test(StreamPrintService sps,
                             Class<? extends Attribute> ac) {
        if (!sps.isAttributeCategorySupported(ac)) {
            return;
        }
        DocFlavor[] dfs = sps.getSupportedDocFlavors();
        for (DocFlavor f : dfs) {
            Attribute[] attrs = (Attribute[])
               sps.getSupportedAttributeValues(ac, f, null);
            if (attrs == null) {
               continue;
            }
            for (Attribute a : attrs) {
                if (!sps.isAttributeValueSupported(a, f, null)) {
                    allSupported = false;
                    System.out.println("Unsupported : " + f + " " + a);
                }
            }
        }
    }
}
