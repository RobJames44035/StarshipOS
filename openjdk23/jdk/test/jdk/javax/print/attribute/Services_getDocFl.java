/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;

/*
 * @test
 * @key printer
 * @bug 4901243 8040139 8167291
 * @summary JPG, GIF, and PNG DocFlavors (URL) should be supported if Postscript is supported.
 * @run main Services_getDocFl
*/


public class Services_getDocFl {
    public static void main (String [] args) {

        HashPrintRequestAttributeSet prSet = null;
        boolean psSupported = false,
            pngImagesSupported = false,
            gifImagesSupported = false,
            jpgImagesSupported = false;
        String mimeType;

        PrintService[] serv = PrintServiceLookup.lookupPrintServices(null, null);
        if (serv.length==0) {
            System.out.println("no PrintService  found");
        } else {
            System.out.println("number of Services "+serv.length);
        }


        for (int i = 0; i<serv.length ;i++) {
            System.out.println("           PRINT SERVICE: "+ i+" "+serv[i]);
            DocFlavor[] flavors = serv[i].getSupportedDocFlavors();
            pngImagesSupported = false;
            gifImagesSupported = false;
            jpgImagesSupported = false;
            psSupported = false;
            for (int j=0; j<flavors.length; j++) {
                System.out.println(flavors[j]);

                if (flavors[j].equals(DocFlavor.URL.PNG)) {
                    pngImagesSupported = true;
                } else if (flavors[j].equals(DocFlavor.URL.GIF)) {
                    gifImagesSupported = true;
                } else if (flavors[j].equals(DocFlavor.URL.JPEG)) {
                    jpgImagesSupported = true;
                } else if (flavors[j].getMimeType().indexOf("postscript") != -1) {
                    psSupported = true;
                }
            }

            if (psSupported && !(pngImagesSupported && gifImagesSupported &&
                jpgImagesSupported)) {

                throw new RuntimeException("Error: URL image DocFlavors are not reported as supported");
            }
        }

    }
}

