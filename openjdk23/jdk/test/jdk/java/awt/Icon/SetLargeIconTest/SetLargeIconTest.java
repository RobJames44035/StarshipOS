/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/*
 * @test
 * @key headful
 * @bug 6425089
 * @summary PIT. Frame does not show a big size jpg image as icon
 * @requires (os.family != "mac")
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual SetLargeIconTest
 */

public class SetLargeIconTest {
    private static final String INSTRUCTIONS = """
            Case 1: Press "Pass" button if this frame does not have icon with green color.

            Case 2: Press "Change to red" if the frame icon is in green color.
            For case 2, press "Pass" button if green icon changes to a larger red icon,
            press "Fail" otherwise.
            """;
    private static JFrame frame;

    private static void createAndShowGUI() {
        frame = new JFrame();

        setColoredIcon(Color.green, 128, 128);
        JButton btnChangeIcon = new JButton("Change to red");
        btnChangeIcon.addActionListener(e -> setColoredIcon(Color.red, 400, 400));

        frame.add(btnChangeIcon, BorderLayout.CENTER);
        frame.setSize(200,65);

        PassFailJFrame.addTestWindow(frame);
        PassFailJFrame.positionTestWindow(frame,
                PassFailJFrame.Position.HORIZONTAL);
        frame.setVisible(true);
    }

    private static void setColoredIcon(Color color, int width, int height) {
        BufferedImage image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
        Graphics gr = image.createGraphics();
        gr.setColor(color);
        gr.fillRect(0, 0, width, height);

        ArrayList<Image> imageList = new java.util.ArrayList<>();
        imageList.add(image);

        frame.setIconImages(imageList);
    }

    public static void main(String[] args) throws Exception {
        PassFailJFrame passFailJFrame = new PassFailJFrame("Large Icon " +
                "Test Instructions", INSTRUCTIONS, 5, 8, 50);
        SwingUtilities.invokeAndWait(SetLargeIconTest::createAndShowGUI);
        passFailJFrame.awaitAndCheck();
    }
}
