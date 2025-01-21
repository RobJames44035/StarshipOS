/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

/*
 * @test
 * @key printer
 * @run main/othervm SmallPaperPrinting
 * @run main/othervm SmallPaperPrinting 1
 * @run main/othervm SmallPaperPrinting 2
 */

public class SmallPaperPrinting
{
   public static void main(String args[]) {
      System.out.println("----------------- Instructions --------------------");
      System.out.println("Arguments: (none)  - paper width=1,     height=.0001");
      System.out.println("              1    - paper width=.0001, height=1");
      System.out.println("              2    - paper width=-1,    height=1");
      System.out.println("A passing test should catch a PrinterException");
      System.out.println("and should display \"Print error: (exception msg)\".");
      System.out.println("---------------------------------------------------\n");
      PrinterJob job = PrinterJob.getPrinterJob();
      PageFormat format = job.defaultPage();
      Paper paper = format.getPaper();

      double w = 1, h = .0001;  // Generates ArithmeticException: / by zero.
      if (args.length > 0 && args[0].equals("1")) {
          w = .0001;  h = 1;
      }  // Generates IllegalArgumentException.
      else if (args.length > 0 && args[0].equals("2")) {
          w = -1;  h = 1;
      }  // Generates NegativeArraySizeException.
      paper.setSize(w, h);
      paper.setImageableArea(0, 0, w, h);
      format.setPaper(paper);
      job.setPrintable(
            new Printable() {
               public int print(Graphics g, PageFormat page_format, int page) {
                  return NO_SUCH_PAGE;
               }
            }, format);

      try {
          job.print();
      } catch (PrinterException e) {
          System.err.println("Print error:\n" + e.getMessage()); // Passing test!
      }
   }
}
