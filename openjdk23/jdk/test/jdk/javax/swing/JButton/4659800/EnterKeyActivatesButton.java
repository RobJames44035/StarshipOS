/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

import java.awt.event.KeyEvent;
import java.awt.Robot;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import static java.util.stream.Collectors.toList;

/*
 * @test
 * @key headful
 * @requires (os.family == "windows")
 * @bug 4659800
 * @summary Check whether pressing <Enter> key generates
 * ActionEvent on focused Button or not. This is applicable only for
 * WindowsLookAndFeel and WindowsClassicLookAndFeel.
 * @run main EnterKeyActivatesButton
 */
public class EnterKeyActivatesButton {
    private static volatile boolean buttonPressed;
    private static JFrame frame;

    public static void main(String[] s) throws Exception {
        runTest();
    }

    private static void setLookAndFeel(String lafName) {
        try {
            UIManager.setLookAndFeel(lafName);
        } catch (UnsupportedLookAndFeelException ignored) {
            System.out.println("Ignoring Unsupported L&F: " + lafName);
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static void disposeFrame() {
        if (frame != null) {
            frame.dispose();
            frame = null;
        }
    }

    private static void createUI() {
        frame = new JFrame();
        JPanel panel = new JPanel();
        JButton focusedButton = new JButton("Button1");
        focusedButton.addActionListener(e -> buttonPressed = true);
        panel.add(focusedButton);
        panel.add(new JButton("Button2"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        focusedButton.requestFocusInWindow();
    }

    public static void runTest() throws Exception {
        Robot robot = new Robot();
        robot.setAutoDelay(100);
        // Consider only Windows and Windows Classic LnFs.
        List<String> winlafs = Arrays.stream(UIManager.getInstalledLookAndFeels())
                                     .filter(laf -> laf.getName().startsWith("Windows"))
                                     .map(laf -> laf.getClassName())
                                     .collect(toList());

        for (String laf : winlafs) {
            try {
                buttonPressed = false;
                System.out.println("Testing L&F: " + laf);
                SwingUtilities.invokeAndWait(() -> {
                    setLookAndFeel(laf);
                    createUI();
                });

                robot.waitForIdle();
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
                robot.waitForIdle();

                if (buttonPressed) {
                    System.out.println("Test Passed for L&F: " + laf);
                } else {
                    throw new RuntimeException("Test Failed, button not pressed for L&F: " + laf);
                }

            } finally {
                SwingUtilities.invokeAndWait(EnterKeyActivatesButton::disposeFrame);
            }
        }

    }
}
