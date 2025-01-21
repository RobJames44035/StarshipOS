/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4412522
 * @summary  Tests if HTML that has comments inside of tables is rendered correctly
 * @key headful
 * @run main bug4412522
*/

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.text.View;
import javax.swing.text.html.HTMLEditorKit;

import java.awt.Robot;
import java.awt.Shape;

public class bug4412522 {

    private static boolean passed = false;

    private static JEditorPane jep;
    private static JFrame f;
    private static Robot robot;

    public void init() {

        String text =
                "<html><head><table border>" +
                "<tr><td>first cell</td><td>second cell</td></tr>" +
                "<tr><!-- this is a comment --><td>row heading</td></tr>" +
                "</table></body></html>";

        JFrame f = new JFrame();
        jep = new JEditorPane();
        jep.setEditorKit(new HTMLEditorKit());
        jep.setEditable(false);

        jep.setText(text);

        f.getContentPane().add(jep);
        f.setSize(500,500);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }


    public static void main(String args[]) throws Exception {
        robot = new Robot();
        robot.setAutoDelay(100);
        bug4412522 test = new bug4412522();
        try {
            SwingUtilities.invokeAndWait(() -> test.init());
            robot.waitForIdle();
            robot.delay(1000);
            Shape r = jep.getBounds();
            View v = jep.getUI().getRootView(jep);
            int tableWidth = 0;
            int cellsWidth = 0;

            while (!(v instanceof javax.swing.text.html.ParagraphView)) {

                int n = v.getViewCount();
                Shape sh = v.getChildAllocation(n - 1, r);
                String viewName = v.getClass().getName();
                if (viewName.endsWith("TableView")) {
                    tableWidth = r.getBounds().width;
                }

                if (viewName.endsWith("CellView")) {
                    cellsWidth = r.getBounds().x + r.getBounds().width;
                }

                v = v.getView(n - 1);
                if (sh != null) {
                    r = sh;
                }
            }

            passed = ((tableWidth - cellsWidth) > 10);
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
}
