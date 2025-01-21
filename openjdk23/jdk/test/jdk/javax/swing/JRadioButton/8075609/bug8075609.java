/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @key headful
 * @bug 8075609
 * @summary  IllegalArgumentException when transferring focus from JRadioButton using tab
 * @run main bug8075609
 */
import java.awt.BorderLayout;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.LayoutFocusTraversalPolicy;
import javax.swing.SwingUtilities;

public class bug8075609 {
    private static Robot robot;
    private static JTextField textField;
    private static JFrame mainFrame;

    public static void main(String[] args) throws Throwable {
        try {
            SwingUtilities.invokeAndWait(bug8075609::createAndShowGUI);

            robot = new Robot();
            robot.waitForIdle();
            robot.delay(1000);
            robot.setAutoDelay(100);

            // Radio button group tab key test
            runTest1();
        } finally {
            SwingUtilities.invokeAndWait(() -> {
                if (mainFrame != null) {
                    mainFrame.dispose();
                }
            });
        }
    }

    private static void createAndShowGUI() {
        mainFrame = new JFrame("Bug 8075609 - 1 test");

        JPanel rootPanel = new JPanel();
        rootPanel.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setFocusTraversalPolicy(new LayoutFocusTraversalPolicy());
        formPanel.setFocusCycleRoot(true);

        JRadioButton option1 = new JRadioButton("Option 1", true);
        JRadioButton option2 = new JRadioButton("Option 2");

        ButtonGroup radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(option1);
        radioButtonGroup.add(option2);

        formPanel.add(option1);
        formPanel.add(option2);
        textField = new JTextField("Another focusable component");
        formPanel.add(textField);

        rootPanel.add(formPanel, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        rootPanel.add(okButton, BorderLayout.SOUTH);

        mainFrame.add(rootPanel);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        mainFrame.toFront();
    }

    // Radio button Group as a single component when traversing through tab key
    private static void runTest1() throws Exception {
        hitKey(KeyEvent.VK_TAB);

        robot.delay(1000);
        SwingUtilities.invokeAndWait(() -> {
            if (!textField.hasFocus()) {
                System.out.println("Radio Button Group Go To Next Component through Tab Key failed");
                throw new RuntimeException("Focus is not on textField as Expected");
            }
        });
    }

    private static void hitKey(int keycode) {
        robot.keyPress(keycode);
        robot.keyRelease(keycode);
        robot.waitForIdle();
    }
}
