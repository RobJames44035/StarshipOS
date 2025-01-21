/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

import java.awt.BorderLayout;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultButtonModel;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;


/*
 * @test
 * @key headful
 * @bug 8240877
 * @summary Verify NPE at FormView.appendBuffer with null option values
 */
public class TestFormViewNPE {

    private static JEditorPane html;
    private static JFrame frame;
    private final static int width = 200;
    private final static int height = 100;

    private static void setup() {
        html = new JEditorPane("text/html",
                "<html><body><form action=\"http://localhost.cgi\">"
                        + "<select name=select id=\"myCourses\" autofocus> \n" +
                        "            <option ></option> \n" +
                        "        </select> "
                        + "<input type=submit name=submit value=\"submit\"/>"
                        + "</form></body></html>");
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.add(html, BorderLayout.CENTER);
        frame.setSize(width, height);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        final Robot robot = new Robot();
        robot.setAutoDelay(100);

        try {
            SwingUtilities.invokeAndWait(() -> setup());
            robot.waitForIdle();
            robot.delay(500);
            robot.mouseMove(width/2, height/2);
            robot.delay(500);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        } finally {
            SwingUtilities.invokeAndWait(() -> frame.dispose());
        }
    }
}
