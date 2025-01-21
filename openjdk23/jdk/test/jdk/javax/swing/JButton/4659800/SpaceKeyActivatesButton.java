/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

import java.awt.Robot;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
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
 * @bug 8281738
 * @summary Check whether pressing <Space> key generates
 *          ActionEvent on focused Button or not.
 * @run main SpaceKeyActivatesButton
 */
public class SpaceKeyActivatesButton {

    private static volatile boolean buttonPressed;
    private static JFrame frame;
    private static JButton focusedButton;
    private static CountDownLatch buttonGainedFocusLatch;

    public static void main(String[] s) throws Exception {
        runTest();
    }

    public static void runTest() throws Exception {
        Robot robot = new Robot();
        robot.setAutoDelay(100);
        robot.setAutoWaitForIdle(true);

        List<String> lafs = Arrays.stream(UIManager.getInstalledLookAndFeels())
                                  .map(laf -> laf.getClassName())
                                  .collect(toList());
        for (String laf : lafs) {
            buttonGainedFocusLatch = new CountDownLatch(1);
            try {
                buttonPressed = false;
                System.out.println("Testing laf : " + laf);
                AtomicBoolean lafSetSuccess = new AtomicBoolean(false);
                SwingUtilities.invokeAndWait(() -> {
                    lafSetSuccess.set(setLookAndFeel(laf));
                    // Call createUI() only if setting laf succeeded
                    if (lafSetSuccess.get()) {
                        createUI();
                    }
                });
                // If setting laf failed, then just get next laf and continue
                if (!lafSetSuccess.get()) {
                    continue;
                }
                robot.waitForIdle();

                // Wait until the button2 gains focus.
                if (!buttonGainedFocusLatch.await(3, TimeUnit.SECONDS)) {
                    throw new RuntimeException("Test Failed, waited too long, " +
                            "but the button can't gain focus for laf : " + laf);
                }

                robot.keyPress(KeyEvent.VK_SPACE);
                robot.keyRelease(KeyEvent.VK_SPACE);

                if (buttonPressed) {
                    System.out.println("Test Passed for laf : " + laf);
                } else {
                    throw new RuntimeException("Test Failed, button not pressed for laf : " + laf);
                }

            } finally {
                SwingUtilities.invokeAndWait(SpaceKeyActivatesButton::disposeFrame);
            }
        }

    }

    private static boolean setLookAndFeel(String lafName) {
        try {
            UIManager.setLookAndFeel(lafName);
        } catch (UnsupportedLookAndFeelException ignored) {
            System.out.println("Ignoring Unsupported laf : " + lafName);
            return false;
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    private static void createUI() {
        frame = new JFrame();
        JPanel panel = new JPanel();
        panel.add(new JButton("Button1"));
        focusedButton = new JButton("Button2");
        focusedButton.addActionListener(e -> buttonPressed = true);
        focusedButton.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                buttonGainedFocusLatch.countDown();
            }
        });
        panel.add(focusedButton);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        focusedButton.requestFocusInWindow();
    }

    private static void disposeFrame() {
        if (frame != null) {
            frame.dispose();
            frame = null;
        }
    }
}
