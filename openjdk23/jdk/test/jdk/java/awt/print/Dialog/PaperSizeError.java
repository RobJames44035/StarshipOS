/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/**
 * @test
 * @bug 6360339 8343224
 * @key printer
 * @summary Test for fp error in paper size calculations.
 * @run main/manual PaperSizeError
 */

import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.Size2DSyntax;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;

public class PaperSizeError {

  static String[] instructions = {
     "This test assumes and requires that you have a printer installed",
     "Two page dialogs will appear. You must press 'OK' on both.",
     "If the test fails, it will throw an Exception.",
     ""
  };

  public static void main(String[] args) {

      for (int i=0;i<instructions.length;i++) {
         System.out.println(instructions[i]);
      }

      /* First find out if we have a valid test environment:
       * ie print service exists and supports A4.
       */
      PrinterJob job = PrinterJob.getPrinterJob();
      PrintService service = job.getPrintService();
      if (service == null ||
          !service.isAttributeValueSupported(MediaSizeName.ISO_A4,
                                             null, null)) {
         return;
      }

      // Create A4 sized PageFormat.
      MediaSize a4 = MediaSize.ISO.A4;
      double a4w = Math.rint((a4.getX(1) * 72.0) / Size2DSyntax.INCH);
      double a4h = Math.rint((a4.getY(1) * 72.0) / Size2DSyntax.INCH);
      System.out.println("Units = 1/72\" size=" + a4w + "x" + a4h);
      Paper paper = new Paper();
      paper.setSize(a4w, a4h);
      PageFormat pf = new PageFormat();
      pf.setPaper(paper);

      // Test dialog with PF argument
      PageFormat newPF = job.pageDialog(pf);
      if (newPF == null) {
          return; // user cancelled the dialog (and hence the test).
      } else {
          verifyPaper(newPF, a4w, a4h);
      }

      PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
      aset.add(OrientationRequested.PORTRAIT);
      aset.add(MediaSizeName.ISO_A4);

      // Test dialog with AttributeSet argument
      newPF = job.pageDialog(aset);
      if (newPF == null) {
          return; // user cancelled the dialog (and hence the test).
      } else {
          verifyPaper(newPF, a4w, a4h);
      }
  }

  static void verifyPaper(PageFormat pf , double a4w, double a4h) {

      double dw1 = pf.getWidth();
      double dh1 = pf.getHeight();
      float fwMM = (float)((dw1 * 25.4) / 72.0);
      float fhMM = (float)((dh1 * 25.4) / 72.0);
      MediaSizeName msn = MediaSize.findMedia(fwMM, fhMM, Size2DSyntax.MM);
      System.out.println("Units = 1/72\" new size=" + dw1 + "x" + dh1);
      System.out.println("Units = MM new size=" + fwMM + "x" + fhMM);
      System.out.println("Media = " + msn);
      if (a4w != Math.rint(dw1) || a4h != Math.rint(dh1)) {
         System.out.println("Got " + Math.rint(dw1) + "x" + Math.rint(dh1) +
                            ". Expected " + a4w + "x" + a4h);
         throw new RuntimeException("Size is not close enough to A4 size");
      }
      // So far as I know, there's no other standard size that is A4.
      // So we should match the right one.
      if (msn != MediaSizeName.ISO_A4) {
          throw new RuntimeException("MediaSizeName is not A4: " + msn);
      }
  }
}
