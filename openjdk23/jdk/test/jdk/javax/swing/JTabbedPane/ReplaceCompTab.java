/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4213896 4228439
 * @summary Ensure that inserting a new tab with a component
 * where that component already exists as another tab is handled
 * properly. The old tab should be removed and the new tab added.
 * @key headful
 * @run main ReplaceCompTab
 */

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

public class ReplaceCompTab {
    static JFrame f;

    public static void main(String[] args) throws Exception {
        try {
            SwingUtilities.invokeAndWait(() -> {
                f = new JFrame("ReplaceCompTab");
                JTabbedPane tabbedpane = new JTabbedPane();
                f.getContentPane().add(tabbedpane, BorderLayout.CENTER);

                JPanel comp = new JPanel();

                // Add first tab
                tabbedpane.addTab("First(temp)", comp);

                // Add second tab with same component (should just replace first one)
                tabbedpane.insertTab("First", null, comp, "component added next", 0);

                // Check to ensure only a single tab exists
                if (tabbedpane.getTabCount() > 1) {
                    throw new RuntimeException("Only one tab should exist");
                }
                // Check to make sure second tab correctly replaced the first
                if (!(tabbedpane.getTitleAt(0).equals("First"))) {
                    throw new RuntimeException("Tab not replaced correctly");
                }
                // Check to make sure adding null continues to work
                try {
                    tabbedpane.addTab("Second", null);
                } catch (Exception e) {
                    throw new RuntimeException("Adding first null " +
                            "component failed:", e);
                }
                try {
                    tabbedpane.addTab("Third", null);
                } catch (Exception e) {
                    throw new RuntimeException("Adding subsequent null " +
                            "component failed: ", e);
                }
                try {
                    tabbedpane.setComponentAt(1, new JLabel("Second Component"));
                    tabbedpane.setComponentAt(2, new JLabel("Third Component"));
                } catch (Exception e) {
                    throw new RuntimeException("Setting null component " +
                            "to non-null failed: ", e);
                }
            });
        } finally {
            SwingUtilities.invokeAndWait(() -> {
                if (f != null) {
                    f.dispose();
                }
            });
        }
    }
}
