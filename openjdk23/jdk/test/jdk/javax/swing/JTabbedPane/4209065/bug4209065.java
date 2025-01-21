/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

/*
 * @test
 * @bug 4209065
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @summary To test if the style of the text on the tab matches the description.
 * @run main/manual bug4209065
 */

public final class bug4209065 {

    private static JFrame frame;
    private static final String text =
            "If the style of the text on the tabs matches the descriptions," +
                    "\npress PASS.\n\nNOTE: where a large font is used, the" +
                    " text may be larger\nthan the tab height but this is OK" +
                    " and NOT a failure.";

    public static void createAndShowGUI() {

        frame = new JFrame("JTabbedPane");
        JTabbedPane tp = new JTabbedPane();

        tp.addTab("<html><center><font size=+3>big</font></center></html>",
                new JLabel());
        tp.addTab("<html><center><font color=red>red</font></center></html>",
                new JLabel());
        tp.addTab("<html><center><em><b>Bold Italic!</b></em></center></html>",
                new JLabel());

        frame.getContentPane().add(tp);
        frame.setSize(400, 400);

        PassFailJFrame.addTestWindow(frame);
        PassFailJFrame.positionTestWindow(frame,
                PassFailJFrame.Position.HORIZONTAL);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws InterruptedException,
            InvocationTargetException {
        PassFailJFrame passFailJFrame = new PassFailJFrame("JTabbedPane " +
                "Test Instructions", text, 5, 19, 35);
        SwingUtilities.invokeAndWait(bug4209065::createAndShowGUI);
        passFailJFrame.awaitAndCheck();
    }
}
