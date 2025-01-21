/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8264303
 * @summary Test implementation of NSAccessibilityTabPanel protocol peer
 * @author Artem.Semenov@jetbrains.com
 * @run main/manual AccessibleJTabbedPaneTest
 * @requires (os.family == "windows" | os.family == "mac")
 */

import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.SwingUtilities;

import java.util.concurrent.CountDownLatch;

public class AccessibleJTabbedPaneTest extends AccessibleComponentTest {

    @Override
    public CountDownLatch createCountDownLatch() {
        return new CountDownLatch(1);
    }

    void createTabPane() {
        INSTRUCTIONS = "INSTRUCTIONS:\n"
                + "Check a11y of JTabbedPane.\n\n"
                + "Turn screen reader on, and tab to the JTabbedPane.\n"
                + "Use the left and right arrow buttons to move through the tabs.\n\n"
                + "If you can hear selected tab names tab further and press PASS, otherwise press FAIL.\n";

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel panel1 = new JPanel();
        String[] names = {"One", "Two", "Three", "Four", "Five"};
        JList list = new JList(names);
        JLabel fieldName = new JLabel("Text field:");
        JTextField textField = new JTextField("some text");
        fieldName.setLabelFor(textField);
        panel1.add(fieldName);
        panel1.add(textField);
        panel1.add(list);
        tabbedPane.addTab("Tab 1", panel1);
        JPanel panel2 = new JPanel();
        for (int i = 0; i < 5; i++) {
            panel2.add(new JCheckBox("CheckBox " + String.valueOf(i + 1)));
        }
        tabbedPane.addTab("tab 2", panel2);
        JPanel panel = new JPanel();
        panel.add(tabbedPane);

        exceptionString = "AccessibleJTabbedPane test failed!";
        createUI(panel, "AccessibleJTabbedPaneTest");
    }

    public static void main(String[] args) throws Exception {
        AccessibleJTabbedPaneTest test = new AccessibleJTabbedPaneTest();

        countDownLatch = test.createCountDownLatch();
        SwingUtilities.invokeLater(test::createTabPane);
        countDownLatch.await();

        if (!testResult) {
            throw new RuntimeException(a11yTest.exceptionString);
        }
    }
}
