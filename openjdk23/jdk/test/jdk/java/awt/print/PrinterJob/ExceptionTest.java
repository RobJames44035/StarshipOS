/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/**
 * @test
 * @key printer
 * @bug 6467557
 * @summary No exception should be thrown.
 * @run main ExceptionTest
 */

import java.awt.*;
import java.awt.print.*;

public class ExceptionTest {
private TextCanvas c;

public static void main(String args[]) {
    ExceptionTest f = new ExceptionTest();
}

public ExceptionTest() {
    c = new TextCanvas();
    PrinterJob pj = PrinterJob.getPrinterJob();

    if (pj != null) {

        pj.setPageable(c);
        try {
           pj.print();
        } catch (PrinterException pe) {
            if (!(pe.getCause() instanceof IndexOutOfBoundsException)) {
              throw new RuntimeException("initCause of Exception not thrown");
            }
        }
    }
}


class TextCanvas extends Panel implements Pageable, Printable {

    public static final int MAXPAGE = 8;

    public int getNumberOfPages() {
        return MAXPAGE;
    }

    public PageFormat getPageFormat(int pageIndex) {
       if (pageIndex > MAXPAGE) throw new IndexOutOfBoundsException();
           PageFormat pf = new PageFormat();
       return pf;
    }

    public Printable getPrintable(int pageIndex) {
       if (pageIndex == 1) throw new IndexOutOfBoundsException();
       return this;
    }

    public int print(Graphics g, PageFormat pgFmt, int pgIndex) {
        System.out.println("****"+pgIndex);
        return Printable.PAGE_EXISTS;
    }

}

}
