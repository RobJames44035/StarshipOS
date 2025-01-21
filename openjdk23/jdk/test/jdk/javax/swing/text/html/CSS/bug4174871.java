/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

import java.awt.Robot;
import java.awt.Shape;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.View;

/*
 * @test
 * @bug 4174871
 * @key headful
 * @summary Tests if CELLSPACING attribute in HTML table is rendered.
 */

public class bug4174871 {
    private static JFrame frame;
    private static JTextPane pane;
    private static volatile boolean passed = false;

    public static void main(String[] args) throws Exception {
        try {
            Robot robot = new Robot();

            SwingUtilities.invokeAndWait(bug4174871::createAndShowUI);
            robot.waitForIdle();
            robot.delay(500);

            SwingUtilities.invokeAndWait(bug4174871::testUI);

            if (!passed) {
                throw new RuntimeException("Test failed!!" +
                        " CELLSPACING attribute in HTML table is NOT rendered");
            }
        } finally {
            SwingUtilities.invokeAndWait(() -> {
                if (frame != null) {
                    frame.dispose();
                }
            });
        }
    }

    public static void createAndShowUI() {
        pane = new JTextPane();
        pane.setContentType("text/html");
        pane.setText("<html>"
                + "<html><head><table border=1 cellspacing=20>"
                + "<tr><td width=100>one</td><td width=100>two</td><td width=100>three</td></tr>"
                + "</table></body></html>");

        frame = new JFrame("Table CellSpacing Test");
        frame.getContentPane().add(pane);
        frame.setSize(600, 200);
        frame.setVisible(true);
    }

    private static void testUI() {
        int tableWidth = 0;
        Shape r = pane.getBounds();
        View v = pane.getUI().getRootView(pane);

        while (!(v instanceof javax.swing.text.html.ParagraphView)) {
            int n = v.getViewCount();
            Shape sh = v.getChildAllocation(n - 1, r);
            String viewName = v.getClass().getName();
            if (viewName.endsWith("TableView")) {
                tableWidth = r.getBounds().width;
            }
            v = v.getView(n - 1);
            if (sh != null) {
                r = sh;
            }
        }
        // tableWidth should be the sum of TD's widths (300)
        // and cellspacings (80)
        passed = (tableWidth >= 380);
    }
}
