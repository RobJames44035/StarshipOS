/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @key printer
 * @bug 4186119
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @summary Confirm that the clip and transform of the Graphics2D is
 *          affected by the landscape orientation of the PageFormat.
 * @run main/manual SetOrient
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.JButton;

public class SetOrient {
    private static final String INSTRUCTIONS =
            """
             This test prints two pages and sends them to the printer.
             One page is in PORTRAIT orientation and the other is in LANDSCAPE
             orientation. On each page it draws an ellipse inscribed in the clip
             boundary established by the PrinterJob. The ellipse should fill the
             page within the bounds established by the default margins and not
             extend off any end or side of the page. Also, the string "Portrait"
             or "Landscape" should be oriented correctly.
            """;

    public static void main(String[] args) throws Exception {
        PassFailJFrame
                .builder()
                .title("SetOrient Test Instructions")
                .instructions(INSTRUCTIONS)
                .rows((int) INSTRUCTIONS.lines().count() + 2)
                .columns(40)
                .splitUIBottom(SetOrient::createAndShowGUI)
                .build()
                .awaitAndCheck();
    }

    public static JButton createAndShowGUI() {
        JButton btn = new JButton("PRINT");
        btn.addActionListener(e -> {
            PrinterJob pjob = PrinterJob.getPrinterJob();

            Printable p = new Printable() {
                public int print(Graphics g, PageFormat pf, int pageIndex) {
                    Graphics2D g2d = (Graphics2D)g;
                    drawGraphics(g2d, pf);
                    return Printable.PAGE_EXISTS;
                }

                void drawGraphics(Graphics2D g, PageFormat pf) {
                    double ix = pf.getImageableX();
                    double iy = pf.getImageableY();
                    double iw = pf.getImageableWidth();
                    double ih = pf.getImageableHeight();

                    g.setColor(Color.black);
                    g.drawString(((pf.getOrientation() == PageFormat.PORTRAIT)
                                    ? "Portrait" : "Landscape"),
                            (int) (ix + iw / 2), (int) (iy + ih / 2));
                    g.draw(new Ellipse2D.Double(ix, iy, iw, ih));
                }
            };

            Book book = new Book();
            PageFormat pf = pjob.defaultPage();
            pf.setOrientation(PageFormat.PORTRAIT);
            book.append(p, pf);
            pf = pjob.defaultPage();
            pf.setOrientation(PageFormat.LANDSCAPE);
            book.append(p, pf);
            pjob.setPageable(book);

            try {
                pjob.print();
            } catch (PrinterException ex) {
                ex.printStackTrace();
                String msg = "PrinterException: " + ex.getMessage();
                PassFailJFrame.forceFail(msg);
            }
        });
        return btn;
    }
}
