/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/* @test
 * @bug 4196662
 * @summary JToolBar has remove(int) method.
 * @run main bug4196662
 */

import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

public class bug4196662 {
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JToolBar tb = new JToolBar();
            tb.add(new JButton("Button1"));
            JButton bt2 = new JButton("Button2");
            tb.add(bt2);
            tb.add(new JButton("Button3"));
            tb.remove(1);
            if (tb.getComponentCount() != 2 || tb.getComponent(1) == bt2) {
                throw new RuntimeException("Component wasn't removed...");
            }
        });
    }
}
