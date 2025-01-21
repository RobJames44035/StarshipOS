/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/**
 * @test
 * @key printer
 * @bug 4762773 6289206 6324049 6362765
 * @summary Tests that get non-null return list of printable areas.
 * @run main SupportedPrintableAreas
 */


import javax.print.*;
import javax.print.event.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;

public class SupportedPrintableAreas {

  public static void main(String[] args) {
     PrintService[] svc;
     PrintService printer = PrintServiceLookup.lookupDefaultPrintService();
     if (printer == null) {
         svc = PrintServiceLookup.lookupPrintServices(DocFlavor.SERVICE_FORMATTED.PRINTABLE, null);
         if (svc.length == 0) {
             throw new RuntimeException("Printer is required for this test.  TEST ABORTED");
         }
         printer = svc[0];
     }
     System.out.println("PrintService found : "+printer);

     if (!printer.isAttributeCategorySupported(MediaPrintableArea.class)) {
         return;
     }
     Object value = printer.getSupportedAttributeValues(
                    MediaPrintableArea.class, null, null);
     if (!value.getClass().isArray()) {
         throw new RuntimeException("unexpected value");
      }

     PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
     value = printer.getSupportedAttributeValues(
                    MediaPrintableArea.class, null, aset);
     if (!value.getClass().isArray()) {
         throw new RuntimeException("unexpected value");
      }

     Media media = (Media)printer.getDefaultAttributeValue(Media.class);
     aset.add(media);
     value = printer.getSupportedAttributeValues(
                    MediaPrintableArea.class, null, aset);
     if (!value.getClass().isArray()) {
         throw new RuntimeException("unexpected value");
     }

     // test for 6289206
     aset.add(MediaTray.MANUAL);
     value = printer.getSupportedAttributeValues(
                    MediaPrintableArea.class, null, aset);
     if ((value != null) && !value.getClass().isArray()) {
         throw new RuntimeException("unexpected value");
     }
  }
}
