/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/*
 * @test
 * @bug 4473671
 * @summary Test to verify GraphicsEnvironment.getDefaultScreenDevice always
 *          returning first screen
 * @requires (os.family == "windows")
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual DefaultScreenDeviceTest
 */

public class DefaultScreenDeviceTest {
    private static Frame testFrame;

    public static void main(String[] args) throws Exception {
        GraphicsEnvironment ge = GraphicsEnvironment.
                getLocalGraphicsEnvironment();
        GraphicsDevice[] gds = ge.getScreenDevices();
        if (gds.length < 2) {
            System.out.println("Test requires at least 2 displays");
            return;
        }

        String INSTRUCTIONS = """
                1. The test is for systems which allows primary display
                   selection in multiscreen systems.
                   Set the system primary screen to be the rightmost
                   (i.e. the right screen in two screen configuration)
                   This can be done by going to OS Display Settings
                   selecting the screen and checking the 'Use this device
                   as primary monitor' checkbox.
                2. When done, click on 'Frame on Primary Screen' button and
                   see where the frame will pop up
                3. If Primary Frame pops up on the primary display,
                   the test passed, otherwise it failed
                """;
        PassFailJFrame.builder()
                .title("Test Instructions")
                .instructions(INSTRUCTIONS)
                .rows((int) INSTRUCTIONS.lines().count() + 2)
                .columns(35)
                .testUI(initialize())
                .build()
                .awaitAndCheck();
    }

    private static List<Frame> initialize() {
        Frame frame = new Frame("Default screen device test");
        GraphicsConfiguration gc =
                GraphicsEnvironment.getLocalGraphicsEnvironment().
                        getDefaultScreenDevice().getDefaultConfiguration();

        testFrame = new Frame("Primary screen frame", gc);
        frame.setLayout(new BorderLayout());
        frame.setSize(200, 200);

        Button b = new Button("Frame on Primary Screen");
        b.addActionListener(e -> {
            if (testFrame != null) {
                testFrame.setVisible(false);
                testFrame.dispose();
            }

            testFrame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e1) {
                    testFrame.setVisible(false);
                    testFrame.dispose();
                }
            });
            testFrame.add(new Label("This frame should be on the primary screen"));
            testFrame.setBackground(Color.red);
            testFrame.pack();
            Rectangle rect = gc.getBounds();
            testFrame.setLocation(rect.x + 100, rect.y + 100);
            testFrame.setVisible(true);
        });
        frame.add(b);
        return List.of(testFrame, frame);
    }
}
