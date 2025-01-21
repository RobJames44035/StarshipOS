/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/* @test
   @bug 5062055
   @summary Tests parsing of incorrect HR attributes
   @author Peter Zhelezniakov
   @run main Test5062055
*/

import java.awt.Dimension;
import javax.swing.*;

public class Test5062055 implements Runnable
{
    public static void main(String argv[]) {
        SwingUtilities.invokeLater(new Test5062055());
        // give HTML time to be parsed
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            throw new Error("Wait interrupted");
        }
    }

    public void run() {
        JEditorPane jep = new JEditorPane();
        jep.setContentType("text/html");
        jep.setEditable(false);
        jep.setText("<HTML><BODY><HR size='5px'></BODY></HTML>");
        jep.setPreferredSize(new Dimension(640,480));
    }
}
