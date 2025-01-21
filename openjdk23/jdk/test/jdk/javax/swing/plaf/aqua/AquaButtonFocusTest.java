/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @requires (os.family == "mac")
 * @bug 8269951
 * @summary Test checks that focus is painted on JButton even
 *          when borders turned off
 * @library ../../regtesthelpers
 * @build Util
 * @run main AquaButtonFocusTest
 */


import javax.swing.JButton;
import javax.swing.UIManager;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class AquaButtonFocusTest {
    public static void main(String[] args) {
        new AquaButtonFocusTest().performTest();
    }

    public void performTest() {
        try {
            UIManager.setLookAndFeel("com.apple.laf.AquaLookAndFeel");
        } catch (Exception e) {
            throw new RuntimeException("Can not initialize Aqua L&F");
        }

        FocusableButton one = new FocusableButton("One");
        one.setSize(100, 100);
        one.setBorderPainted(false);
        BufferedImage noFocus = new BufferedImage(200, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics g = noFocus.createGraphics();
        one.paint(g);
        g.dispose();
        BufferedImage focus = new BufferedImage(200, 100, BufferedImage.TYPE_INT_ARGB);
        one.setFocusOwner(true);
        g = focus.createGraphics();
        one.paint(g);
        g.dispose();
        if (Util.compareBufferedImages(noFocus, focus)) {
            throw new RuntimeException("Focus is not painted on JButton");
        }
    }

    class FocusableButton extends JButton {
        private boolean focusOwner = false;

        public FocusableButton(String label) {
            super(label);
        }

        public void setFocusOwner(boolean focused) {
            this.focusOwner = focused;
        }

        @Override
        public boolean isFocusOwner() {
            return focusOwner;
        }

        @Override
        public boolean hasFocus() {
            return this.focusOwner;
        }
    }
}
