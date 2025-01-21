/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;

import javax.swing.JPanel;

/*
 * @test
 * @bug 4427483
 * @summary Arabic text followed by newline should have no missing glyphs
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual ArabicBox
 */
public final class ArabicBox {

    private static final String TEXT =
            "\u0627\u0644\u0639\u0631\u0628\u064A\u0629\n";

    private static final String FONT_NAME = Font.DIALOG;

    private static final String INSTRUCTIONS = """
            In the below panel, you should see the following text:

            """
            + TEXT + """
            (It's \u2018Arabic\u2019 in Arabic.)

            If there are no 'box glyphs' for missing glyphs,
            press Pass; otherwise, press Fail.""";

    public static void main(String[] args) throws Exception {
        final Font font = new Font(FONT_NAME, Font.PLAIN, 24);
        System.out.println("asked for " + FONT_NAME + " and got: " + font.getFontName());

        PassFailJFrame.builder()
                      .title("Arabic Box")
                      .instructions(INSTRUCTIONS)
                      .rows(7)
                      .columns(40)
                      .splitUIBottom(() -> createPanel(font))
                      .build()
                      .awaitAndCheck();
    }

    private static JPanel createPanel(Font font) {
        return new TextPanel(font);
    }

    private static final class TextPanel extends JPanel {
        private TextLayout layout;

        private TextPanel(Font font) {
            setForeground(Color.black);
            setBackground(Color.white);
            setFont(font);
            setPreferredSize(new Dimension(300, 150));
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2d = (Graphics2D)g;
            if (layout == null) {
                Font font = g2d.getFont();
                FontRenderContext frc = g2d.getFontRenderContext();

                layout = new TextLayout(TEXT, font, frc);
                System.out.println(layout.getBounds());
            }

            layout.draw(g2d, 10, 50);
            g2d.drawString(TEXT, 10, 100);
        }
    }
}
