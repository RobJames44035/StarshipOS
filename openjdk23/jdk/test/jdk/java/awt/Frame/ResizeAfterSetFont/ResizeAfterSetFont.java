/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 @test
 @key headful
 @bug 7170655
 @summary Frame size does not change after changing font
 @author Jonathan Lu
 @library ../../regtesthelpers
 @build Util
 @run main ResizeAfterSetFont
 */

import java.awt.*;
import test.java.awt.regtesthelpers.Util;

public class ResizeAfterSetFont {

    public static void main(String[] args) throws Exception {
        Frame frame = new Frame("bug7170655");
        frame.setLayout(new BorderLayout());
        frame.setBackground(Color.LIGHT_GRAY);

        Panel panel = new Panel();
        panel.setLayout(new GridLayout(0, 1, 1, 1));

        Label label = new Label("Test Label");
        label.setBackground(Color.white);
        label.setForeground(Color.RED);
        label.setFont(new Font("Dialog", Font.PLAIN, 12));

        panel.add(label);
        frame.add(panel, "South");
        frame.pack();
        frame.setVisible(true);

        Util.waitForIdle(null);

        Dimension dimBefore = frame.getSize();
        label.setFont(new Font("Dialog", Font.PLAIN, 24));

        frame.validate();
        frame.pack();
        Dimension dimAfter = frame.getSize();

        if (dimBefore.equals(dimAfter)) {
            throw new Exception(
                    "Frame size does not change after Label.setFont()!");
        }
    }
}
