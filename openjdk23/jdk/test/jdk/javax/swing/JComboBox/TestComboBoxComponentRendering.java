/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8218474
 * @key headful
 * @requires (os.family == "linux")
 * @summary Verifies if combobox components are rendered correctly.
 * @run main TestComboBoxComponentRendering
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class TestComboBoxComponentRendering {
    private static JFrame frame;
    private static JComboBox cb;
    private static Robot robot;

    public static void main(String[] args) throws Exception {
        robot = new Robot();
        robot.setAutoDelay(100);

        for (UIManager.LookAndFeelInfo laf :
                UIManager.getInstalledLookAndFeels()) {
            if (!laf.getClassName().contains("MotifLookAndFeel") &&
                !laf.getClassName().contains("MetalLookAndFeel")) {
                System.out.println("Testing LAF: " + laf.getClassName());
                SwingUtilities.invokeAndWait(() -> setLookAndFeel(laf));
                doTesting(laf);
            }
        }
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

    private static void doTesting(UIManager.LookAndFeelInfo laf)
            throws Exception {
        try {
            SwingUtilities.invokeAndWait(() -> {
                createAndShowUI();
            });
            boolean passed = false;
            robot.waitForIdle();
            robot.delay(1000);

            Point pt = cb.getLocationOnScreen();
            BufferedImage img = robot.createScreenCapture(
                    new Rectangle(pt.x, pt.y, cb.getWidth(), cb.getHeight()));
            for (int x = 20; x < img.getWidth()-20; ++x) {
                for (int y = 20; y < img.getHeight()-20; ++y) {
                    if (img.getRGB(x,y) == Color.RED.getRGB()) {
                        passed = true;
                        break;
                    }
                }
                if (passed)
                    break;
            }

            if (passed) {
                System.out.println("Passed");
            } else {
                ImageIO.write(img, "png",
                        new File("ComboBox.png"));
                throw new RuntimeException("ComboBox components not rendered" +
                        " correctly for: " + laf.getClassName());
            }
        } finally {
            SwingUtilities.invokeAndWait(() -> {
                if (frame != null) {
                    frame.dispose();
                }
            });
        }
    }

    private static void createAndShowUI() {
        String[] petStrings = { "Bird", "Cat", "Dog", "Rabbit", "Pig" };
        frame = new JFrame();
        cb = new JComboBox(petStrings);
        cb.setRenderer(new ComboBoxCustomRenderer());
        frame.pack();
        frame.add(cb);
        frame.setSize(200,250);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class ComboBoxCustomRenderer extends JLabel
        implements ListCellRenderer {

    public ComboBoxCustomRenderer() {
        setOpaque(true);
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
    }

    public Component getListCellRendererComponent(JList list, Object value,
                        int index, boolean isSelected, boolean cellHasFocus) {
        setText(value.toString());
        setForeground(Color.RED);
        return this;
    }
}
