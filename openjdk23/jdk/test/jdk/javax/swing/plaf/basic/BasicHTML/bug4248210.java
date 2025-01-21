/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;

/*
 * @test
 * @bug 4248210
 * @key headful
 * @summary Tests that HTML in JLabel is painted using LAF-defined
            foreground color
 * @run main bug4248210
 */

public class bug4248210 {
    private static final Color labelColor = Color.red;

    public static void main(String[] args) throws Exception {
        for (UIManager.LookAndFeelInfo laf :
                UIManager.getInstalledLookAndFeels()) {
            if (!(laf.getName().contains("Motif") || laf.getName().contains("GTK"))) {
                System.out.println("Testing LAF: " + laf.getName());
                SwingUtilities.invokeAndWait(() -> test(laf));
            }
        }
    }

    private static void test(UIManager.LookAndFeelInfo laf) {
        setLookAndFeel(laf);
        if (UIManager.getLookAndFeel() instanceof NimbusLookAndFeel) {
            // reset "basic" properties
            UIManager.getDefaults().put("Label.foreground", null);
            // set "synth - nimbus" properties
            UIManager.getDefaults().put("Label[Enabled].textForeground", labelColor);
        } else {
            // reset "synth - nimbus" properties
            UIManager.getDefaults().put("Label[Enabled].textForeground", null);
            // set "basic" properties
            UIManager.getDefaults().put("Label.foreground", labelColor);
        }

        JLabel label = new JLabel("<html><body>Can You Read This?</body></html>");
        label.setSize(150, 30);

        BufferedImage img = paintToImage(label);
        if (!chkImgForegroundColor(img)) {
            try {
                ImageIO.write(img, "png", new File("Label_" + laf.getName() + ".png"));
            } catch (IOException ignored) {}
            throw new RuntimeException("JLabel not painted with LAF defined " +
                    "foreground color");
        }
        System.out.println("Test Passed");
    }

    private static void setLookAndFeel(UIManager.LookAndFeelInfo laf) {
        try {
            UIManager.setLookAndFeel(laf.getClassName());
        } catch (UnsupportedLookAndFeelException ignored) {
            System.out.println("Unsupported LAF: " + laf.getClassName());
        } catch (ClassNotFoundException | InstantiationException
                 | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static BufferedImage paintToImage(JComponent content) {
        BufferedImage im = new BufferedImage(content.getWidth(), content.getHeight(),
                TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) im.getGraphics();
        g.setBackground(Color.WHITE);
        g.clearRect(0, 0, content.getWidth(), content.getHeight());
        content.paint(g);
        g.dispose();
        return im;
    }

    private static boolean chkImgForegroundColor(BufferedImage img) {
        for (int x = 0; x < img.getWidth(); ++x) {
            for (int y = 0; y < img.getHeight(); ++y) {
                if (img.getRGB(x, y) == labelColor.getRGB()) {
                    return true;
                }
            }
        }
        return false;
    }
}
