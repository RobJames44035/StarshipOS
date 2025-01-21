/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4623342
 * @summary  Tests if InlineView causes extra spacing around images in JTable
 * @key headful
 * @run main bug4623342
 */

import java.awt.Robot;
import java.awt.Shape;

import javax.swing.JFrame;
import javax.swing.JEditorPane;
import javax.swing.SwingUtilities;
import javax.swing.text.View;
import javax.swing.text.html.HTMLEditorKit;

public class bug4623342 {

    private static volatile boolean passed;

    private JEditorPane jep;
    private static JFrame f;

    public static void main(String[] args) throws Exception {
        Robot robot = new Robot();
        robot.setAutoDelay(100);
        try {
            bug4623342 test = new bug4623342();
            SwingUtilities.invokeAndWait(test::init);
            robot.waitForIdle();
            robot.delay(100);
            SwingUtilities.invokeAndWait(test::start);
            if (!passed) {
                throw new RuntimeException("Test failed.");
            }
        } finally {
            SwingUtilities.invokeAndWait(() -> {
                if (f != null) {
                    f.dispose();
                }
            });
        }
    }

    public void init() {

        String text =
            "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">" +
             "<tr><td width=\"10\" height=\"23\">" +
               "<img src=\"file:/a.jpg\" width=65 height=23 border=\"0\"></td></tr>" +
             "<tr><td width=\"10\" height=\"23\">" +
               "<img src=\"file:/a.jpg\" width=65 height=23 border=\"0\"></td></tr></table>";

        f = new JFrame();
        jep = new JEditorPane();
        jep.setEditorKit(new HTMLEditorKit());
        jep.setEditable(false);

        jep.setText(text);

        f.getContentPane().add(jep);
        f.setSize(500, 500);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    private void start() {
        Shape r = jep.getBounds();
        View v = jep.getUI().getRootView(jep);
        int tableHeight = 0;
        while (!(v instanceof javax.swing.text.html.ParagraphView)) {
            int n = v.getViewCount();
            Shape sh = v.getChildAllocation(n - 1, r);
            String viewName = v.getClass().getName();
            if (viewName.endsWith("TableView")) {
                tableHeight = r.getBounds().height;
            }
            v = v.getView(n - 1);
            if (sh != null) {
                r = sh;
            }
        }
        // tableHeight should be the sum of TD's heights (46)
        passed = (tableHeight == 46);
    }
}
