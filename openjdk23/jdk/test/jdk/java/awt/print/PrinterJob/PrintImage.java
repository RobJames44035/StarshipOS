/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

/*
 * @test
 * @bug 4298489
 * @summary Confirm that output is same as screen.
 * @key printer
 * @requires os.family=="windows"
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual PrintImage
 */
public class PrintImage extends Frame implements ActionListener {
    private final PrintImageCanvas printImageCanvas = new PrintImageCanvas();
    private final MenuItem print1Menu = new MenuItem("PrintTest1");
    private final MenuItem print2Menu = new MenuItem("PrintTest2");
    private static final String INSTRUCTIONS =
            "Select PrintTest1 in the File menu.\n" +
            "Print Dialog will appear.\n" +
            "Click OK to start the first print job.\n" +
            "\n" +
            "Select PrintTest2 in the File menu.\n" +
            "Page Setup Dialog will appear.\n" +
            "Click OK.\n" +
            "Print Dialog will appear.\n" +
            "Click OK to start the second print job.\n" +
            "\n" +
            "The text in the printouts for PrintTest1 and PrintTest2 should be\n" +
            "same as that on the screen.\n" +
            "Press Pass if they are, otherwise press Fail.";

    public static void main(String[] argv) throws Exception {
        if (PrinterJob.lookupPrintServices().length == 0) {
            throw new RuntimeException("Printer not configured or available.");
        }

        PassFailJFrame.builder()
                .instructions(INSTRUCTIONS)
                .testUI(PrintImage::new)
                .rows((int) INSTRUCTIONS.lines().count() + 1)
                .columns(45)
                .build()
                .awaitAndCheck();
    }

    public PrintImage() {
        super("PrintImage");
        initPrintImage();
    }

    public void initPrintImage() {
        initMenu();
        setLayout(new BorderLayout());
        add(printImageCanvas, BorderLayout.CENTER);
        setSize(500, 300);
    }

    private void initMenu() {
        MenuBar mb = new MenuBar();
        Menu me = new Menu("File");
        me.add(print1Menu);
        me.add(print2Menu);
        mb.add(me);
        setMenuBar(mb);

        print1Menu.addActionListener(this);
        print2Menu.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        Object target = e.getSource();
        if (target.equals(print1Menu)) {
            printMain1();
        } else if (target.equals(print2Menu)) {
            printMain2();
        }
    }

    private void printMain1() {
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        PageFormat pageFormat = printerJob.defaultPage();

        printerJob.setPrintable(printImageCanvas, pageFormat);

        if (printerJob.printDialog()) {
            try {
                printerJob.print();
            } catch (PrinterException e) {
                PassFailJFrame.forceFail("Print Failed");
                e.printStackTrace();
            }
        } else {
            printerJob.cancel();
        }
    }

    private void printMain2() {
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        PageFormat pageFormat = printerJob.pageDialog(printerJob.defaultPage());

        printerJob.setPrintable(printImageCanvas, pageFormat);

        if (printerJob.printDialog()) {
            try {
                printerJob.print();
            } catch (PrinterException e) {
                PassFailJFrame.forceFail("Print Failed");
                e.printStackTrace();
            }
        } else {
            printerJob.cancel();
        }
    }

    private static class PrintImageCanvas extends Canvas implements Printable {
        @Override
        public void paint(Graphics g) {
            Font drawFont = new Font("MS Mincho", Font.ITALIC, 50);
            g.setFont(drawFont);
            g.setColor(new Color(0, 0, 0, 200));
            g.drawString("PrintSample!", 100, 150);
        }

        @Override
        public int print(Graphics g, PageFormat pf, int pi)
                throws PrinterException {
            if (pi > 0) {
                return NO_SUCH_PAGE;
            }
            paint(g);
            return PAGE_EXISTS;
        }
    }
}

