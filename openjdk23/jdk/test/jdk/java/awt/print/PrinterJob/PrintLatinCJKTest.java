/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8008535 8022536
 * @library ../../regtesthelpers
 * @build PassFailJFrame
 * @summary JDK7 Printing: CJK and Latin Text in string overlap
 * @key printer
 * @run main/manual PrintLatinCJKTest
 */

import java.awt.Font;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.lang.reflect.InvocationTargetException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

public class PrintLatinCJKTest implements Printable {

    private static final String TEXT = "\u4e00\u4e01\u4e02\u4e03\u4e04English";

    private static final String INFO = """
            Press Print, send the output to the printer and examine it.
            The printout should have text looking like this:

            """
            + TEXT + """


            Press Pass if the text is printed correctly.
            If Japanese and English text overlap, press Fail.


            To test 8022536, if a remote printer is the system default,
            it should show in the dialog as the selected printer.
            """;

    private static JComponent createTestUI() {
        JButton b = new JButton("Print");
        b.addActionListener((ae) -> {
            try {
                PrinterJob job = PrinterJob.getPrinterJob();
                job.setPrintable(new PrintLatinCJKTest());
                if (job.printDialog()) {
                    job.print();
                }
            } catch (PrinterException ex) {
                ex.printStackTrace();
                String msg = "PrinterException: " + ex.getMessage();
                JOptionPane.showMessageDialog(b, msg, "Error occurred",
                                              JOptionPane.ERROR_MESSAGE);
                PassFailJFrame.forceFail(msg);
            }
        });

        Box main = Box.createHorizontalBox();
        main.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        main.add(Box.createHorizontalGlue());
        main.add(b);
        main.add(Box.createHorizontalGlue());
        return main;
    }

    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex)
                         throws PrinterException {
        if (pageIndex > 0) {
            return Printable.NO_SUCH_PAGE;
        }
        g.translate((int) pf.getImageableX(), (int) pf.getImageableY());
        g.setFont(new Font(Font.DIALOG, Font.PLAIN, 36));
        g.drawString(TEXT, 20, 100);
        return Printable.PAGE_EXISTS;
    }

    public static void main(String[] args) throws InterruptedException, InvocationTargetException {
        if (PrinterJob.lookupPrintServices().length == 0) {
            throw new RuntimeException("Printer not configured or available.");
        }

        PassFailJFrame.builder()
                      .title("Print Latin CJK Test")
                      .instructions(INFO)
                      .testTimeOut(10)
                      .rows(12)
                      .columns(30)
                      .splitUI(PrintLatinCJKTest::createTestUI)
                      .build()
                      .awaitAndCheck();
    }
}
