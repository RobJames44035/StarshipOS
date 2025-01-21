/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/* @test
 * @bug 8032667
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @summary [macosx] Components cannot be rendered in HiDPI to BufferedImage
 * @run main/manual bug8032667
 */

public class bug8032667 {

    static final int scale = 2;
    static final int width = 130;
    static final int height = 50;
    static final int scaledWidth = scale * width;
    static final int scaledHeight = scale * height;

    private static final String INSTRUCTIONS = """
            Verify that scaled components are rendered smoothly to image.

            1. Run the test.
            2. Check that Selected and Deselected JCheckBox icons are drawn smoothly.
            If so, press PASS, else press FAIL.
            """;

    public static void main(String[] args) throws Exception {
        PassFailJFrame.builder()
                .title("bug8032667 Test Instructions")
                .instructions(INSTRUCTIONS)
                .rows((int) INSTRUCTIONS.lines().count() + 2)
                .columns(40)
                .testUI(bug8032667::createAndShowGUI)
                .build()
                .awaitAndCheck();
    }

    public static JFrame createAndShowGUI() {
        JFrame frame = new JFrame("bug8032667 HiDPI Component Test");
        frame.setLayout(new BorderLayout());
        frame.setBounds(0, 400, 400, 400);

        final Image image1 = getImage(getCheckBox("Deselected", false));
        final Image image2 = getImage(getCheckBox("Selected", true));
        JPanel panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                g.drawImage(image1, 0, 0, scaledWidth, scaledHeight, this);
                g.drawImage(image2, 0, scaledHeight + 5,
                        scaledWidth, scaledHeight, this);
            }
        };
        frame.add(panel, BorderLayout.CENTER);
        return frame;
    }

    static JCheckBox getCheckBox(String text, boolean selected) {
        JCheckBox checkBox = new JCheckBox(text);
        checkBox.setSelected(selected);
        checkBox.setSize(new Dimension(width, height));
        return checkBox;
    }

    static Image getImage(JComponent component) {
        final BufferedImage image = new BufferedImage(
                scaledWidth, scaledHeight, BufferedImage.TYPE_INT_ARGB);
        final Graphics g = image.getGraphics();
        ((Graphics2D) g).scale(scale, scale);
        component.paint(g);
        g.dispose();

        return image;
    }
}
