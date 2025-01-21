/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;

/*
 * @test
 * @bug 6435804
 * @summary REGRESSION: NetBeans 5.0 icon no longer shows up when you alt-tab on XP
 * @key headful
 * @requires (os.family != "mac")
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual ALTTABIconBeingErased
 */

public class ALTTABIconBeingErased {

    private static final String INSTRUCTIONS =
            "This test verifies that the Frame's icon is not corrupted after showing\n"
                    + "and disposing owned dialog\n"
                    + "You would see a button in a Frame.\n"
                    + "1) The frame should have icon with 2 black and 2 white squares.\n"
                    + "2) Verify that icon appearing on ALT-TAB is also a\n"
                    + "light icon.\n"
                    + "3) Now open a child by pressing on \"Open Child\" button.\n"
                    + "Child Dialog should appear. It should have the same icon as frame.\n"
                    + "4) Now close the dialog by pressing Space or clicking on a button in it.\n"
                    + "Dialog should be disposed now.\n"
                    + "5) Verify that icon on ALT-TAB is the same as before";

    private static Frame frame;
    private static final int SIZE = 300;

    private static void updateIconImage() {
        BufferedImage image = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);

        Graphics gr = image.createGraphics();
        gr.setColor(Color.WHITE);
        gr.fillRect(0, 0, SIZE, SIZE);

        gr.setColor(Color.BLACK);
        gr.fillRect(0, 0, SIZE / 2, SIZE / 2);
        gr.fillRect(SIZE / 2, SIZE / 2, SIZE, SIZE);

        frame.setIconImage(image);
    }

    private static void createAndShowGUI(){
        frame = new Frame();
        Button setImageButton5 = new Button("Open Child");
        updateIconImage();

        setImageButton5.addActionListener(event -> {
            try {
                final Dialog d1 = new Dialog(frame, true);
                d1.setSize(100, 100);
                Button ok = new Button("OK");
                ok.addActionListener(e -> {
                        d1.setVisible(false);
                        d1.dispose();
                });
                d1.add(ok);
                d1.setLocation(frame.getX(), frame.getY() + 70);
                d1.setVisible(true);
            } catch (Exception e) {
                throw new RuntimeException("Test failed because of" +
                        " exception" + e + ". Press Fail.");
            }
        });

        frame.add(setImageButton5, BorderLayout.CENTER);
        frame.setSize(200,65);

        PassFailJFrame.addTestWindow(frame);
        PassFailJFrame.positionTestWindow(frame,
                PassFailJFrame.Position.HORIZONTAL);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws InterruptedException,
                                                  InvocationTargetException {
        PassFailJFrame passFailJFrame = new PassFailJFrame("Large Icon " +
                "Test Instructions", INSTRUCTIONS, 5, 12, 50);
        SwingUtilities.invokeAndWait(ALTTABIconBeingErased::createAndShowGUI);
        passFailJFrame.awaitAndCheck();
    }
}
