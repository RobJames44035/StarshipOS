/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @key headful
 * @bug 6827800
 * @summary Test to check hidden default button does not respond to 'Enter' key
 * @run main HiddenDefaultButtonTest
 */

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class HiddenDefaultButtonTest {

    private static int ButtonClickCount = 0;
    private static JFrame frame;

    private static void createGUI() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton button = new JButton("Default button");
        button.setDefaultCapable(true);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ButtonClickCount++;
            }
        });

        frame.add(button);
        button.setVisible(false);

        frame.getRootPane().setDefaultButton(button);

        frame.setSize(200, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void disposeTestUI() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            frame.dispose();
        });
    }

    private static void test() throws Exception {
        // Create Robot
        Robot testRobot = new Robot();

        testRobot.waitForIdle();

        testRobot.keyPress(KeyEvent.VK_ENTER);
        testRobot.delay(20);
        testRobot.keyRelease(KeyEvent.VK_ENTER);
        testRobot.delay(200);
        testRobot.keyPress(KeyEvent.VK_ENTER);
        testRobot.delay(20);
        testRobot.keyRelease(KeyEvent.VK_ENTER);

        testRobot.waitForIdle();

        if (ButtonClickCount != 0) {
            disposeTestUI();
            throw new RuntimeException("DefaultButton is pressed even if it is invisible");
        }

    }

    public static void main(String[] args) throws Exception {
        // create UI
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                HiddenDefaultButtonTest.createGUI();
            }
        });

        // Test default button press by pressing EnterKey using Robot
        test();

        // dispose UI
        HiddenDefaultButtonTest.disposeTestUI();
    }
}

