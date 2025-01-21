/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;

/*
 * @test
 * @bug 4884389 7183516
 * @key printer
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @summary Font specified with face name loses style on printing
 * @run main/manual PrintAllFonts
 */
public class PrintAllFonts implements Printable {
    private static final int LINE_HEIGHT = 18;
    private static final int FONT_SIZE = 14;

    private final Font[] allFonts =
            GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
    private int fontNum = 0;
    private int startNum = 0;
    private int thisPage = 0;

    private static final String INSTRUCTIONS =
            "This bug is system dependent and is not always reproducible.\n" +
            "Font names will be printed in two columns.\n" +
            "First column non synthesised and second column with synthesised italic.\n" +
            "A passing test will have all text printed with correct font style.";

    public static void main(String[] args) throws Exception {
        if (PrinterJob.lookupPrintServices().length == 0) {
            throw new RuntimeException("Printer not configured or available.");
        }

        PassFailJFrame passFailJFrame = PassFailJFrame.builder()
                .instructions(INSTRUCTIONS)
                .testTimeOut(10)
                .rows((int) INSTRUCTIONS.lines().count() + 1)
                .columns(45)
                .build();

        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setPrintable(new PrintAllFonts());
        if (pj.printDialog()) {
            pj.print();
        } else {
            PassFailJFrame.forceFail("User cancelled printing");
        }
        passFailJFrame.awaitAndCheck();
    }

    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) {
        if (fontNum >= allFonts.length && pageIndex > thisPage) {
            return NO_SUCH_PAGE;
        }
        if (pageIndex > thisPage) {
            startNum = fontNum;
            thisPage = pageIndex;
        } else {
            fontNum = startNum;
        }

        int fontsPerPage = (int) pf.getImageableHeight() / LINE_HEIGHT - 1;
        int x = (int) pf.getImageableX() + 10;
        int y = (int) pf.getImageableY() + LINE_HEIGHT;

        g.setColor(Color.black);
        for (int n = 0; n < fontsPerPage; n++) {
            Font f = allFonts[fontNum].deriveFont(Font.PLAIN, FONT_SIZE);
            Font fi = allFonts[fontNum].deriveFont(Font.ITALIC, FONT_SIZE);
            g.setFont(f);
            g.drawString(f.getFontName(), x, y);
            g.setFont(fi);
            g.drawString(f.getFontName(), (int) (x + pf.getImageableWidth() / 2), y);
            y += LINE_HEIGHT;
            fontNum++;
            if (fontNum >= allFonts.length) {
                break;
            }
        }
        return PAGE_EXISTS;
    }
}
