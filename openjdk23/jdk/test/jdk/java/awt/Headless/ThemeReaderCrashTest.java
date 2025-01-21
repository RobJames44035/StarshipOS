/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8159956
 * @summary Test to check JVM crash is not observed in headless mode while
 * trying to create a JScrollPane
 * @run main/othervm -Djava.awt.headless=true ThemeReaderCrashTest
 */

import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.ScrollPaneConstants;

public class ThemeReaderCrashTest {

    public static void main(String[] args) throws Exception {

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                ThemeReaderCrashTest obj = new ThemeReaderCrashTest();
            }
        });
    }

    ThemeReaderCrashTest() {
        JPanel panel = new JPanel();
        JScrollPane pane =
            new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        panel.setSize(300, 200);

        panel.add(pane);
    }
}

