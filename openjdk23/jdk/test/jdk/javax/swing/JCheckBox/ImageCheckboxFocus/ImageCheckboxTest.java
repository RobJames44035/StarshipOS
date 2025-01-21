/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/*
 * @test
 * @key headful
 * @bug 8216358 8279586
 * @summary [macos] The focus is invisible when tab to "Image Radio Buttons" and "Image CheckBoxes"
 * @library ../../regtesthelpers/
 * @build Util
 * @run main ImageCheckboxTest
 */

public class ImageCheckboxTest {
    public static void main(String[] args) throws Exception {
        ImageCheckboxTest test = new ImageCheckboxTest();
        boolean passed = true;
        // There are bugs found in various LaFs that needs to be fixed
        // to enable testing there
        String[] skip = {
                "GTK+",  // JDK-8281580
                "Nimbus" // JDK-8281581
        };
        testloop:
        for (UIManager.LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
            for (String s : skip) {
                if (s.equals(laf.getName())) {
                    continue testloop;
                }
            }
            passed = passed && test.performTest(laf);
        }

        if(!passed) {
            throw new RuntimeException("Test failed");
        }
    }

    public boolean performTest(UIManager.LookAndFeelInfo laf) throws Exception {
        BufferedImage imageNoFocus = new BufferedImage(100, 50,
                BufferedImage.TYPE_INT_ARGB);
        BufferedImage imageFocus = new BufferedImage(100, 50,
                BufferedImage.TYPE_INT_ARGB);
        BufferedImage imageFocusNotPainted = new BufferedImage(100, 50,
                BufferedImage.TYPE_INT_ARGB);
        boolean success = true;

        try {
            UIManager.setLookAndFeel(laf.getClassName());
        } catch (UnsupportedLookAndFeelException ulaf) {
            return true;
        }
        CustomCheckBox checkbox = new CustomCheckBox("Test", new MyIcon(Color.GREEN));
        checkbox.setFocusPainted(true);
        checkbox.setSize(100, 50);
        checkbox.setFocused(false);
        checkbox.paint(imageNoFocus.createGraphics());
        checkbox.setFocused(true);
        checkbox.paint(imageFocus.createGraphics());

        if (Util.compareBufferedImages(imageFocus, imageNoFocus)) {
            File folder = new File(laf.getName());
            if (!folder.exists()) {
                folder.mkdir();
            }
            ImageIO.write(imageFocus, "png", new File(folder, "/imageFocus.png"));
            ImageIO.write(imageNoFocus, "png", new File(folder, "/imageNoFocus.png"));
            System.err.println(laf.getName() + ": Changing of focus is not visualized");
            success = false;
        }

        checkbox.setFocusPainted(false);
        checkbox.paint(imageFocusNotPainted.createGraphics());

        if (!Util.compareBufferedImages(imageFocusNotPainted, imageNoFocus)) {
            File folder = new File(laf.getName());
            if (!folder.exists()) {
                folder.mkdir();
            }
            ImageIO.write(imageFocusNotPainted, "png",
                    new File(folder,"imageFocusNotPainted.png"));
            ImageIO.write(imageFocus, "png", new File(folder, "imageFocus.png"));
            ImageIO.write(imageNoFocus, "png", new File(folder, "imageNoFocus.png"));
            System.err.println(laf.getName() + ": setFocusPainted(false) is ignored");
            success = false;
        }
        return success;
    }

    class MyIcon implements Icon {
        Color color;
        public MyIcon(Color color) {
            this.color = color;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Color old = g.getColor();
            g.setColor(color);
            g.fillArc(x+2, y+2, 12, 12, 0, 360);
            g.setColor(old);
        }

        @Override
        public int getIconWidth() {
            return 18;
        }

        @Override
        public int getIconHeight() {
            return 18;
        }
    }

    class CustomCheckBox extends JCheckBox {
        public CustomCheckBox(String label, Icon icon) {
            super(label, icon);
        }

        private boolean focused = false;
        public void setFocused(boolean focused) {
            this.focused = focused;
        }

        @Override
        public boolean hasFocus() {
            return focused;
        }
    }
}
