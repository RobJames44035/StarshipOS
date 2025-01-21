/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/*
 * @test
 * @bug 4696826
 * @summary BasicScrollBarUI should check if it needs to paint the thumb
 * @run main bug4696826
 */

public class bug4696826 {
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JScrollBar sb = new JScrollBar();
            sb.setBounds(new Rectangle(0, 0, 20, 20));

            TestScrollBarUI ui = new TestScrollBarUI();
            sb.setUI(ui);
            ui.setThumbBounds(0, 0, 20, 20);

            BufferedImage image = new BufferedImage(100, 100,
                    BufferedImage.TYPE_3BYTE_BGR);
            Graphics g = image.getGraphics();
            g.setClip(200, 200, 100, 100);
            sb.paint(g);
        });
        System.out.println("Test Passed!");
    }

    static class TestScrollBarUI extends BasicScrollBarUI {
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            throw new RuntimeException("Thumb shouldn't be painted");
        }
        public void setThumbBounds(int x, int y, int width, int height) {
            super.setThumbBounds(x, y, width, height);
        }
    }
}
