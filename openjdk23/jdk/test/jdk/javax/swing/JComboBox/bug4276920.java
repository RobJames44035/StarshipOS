/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/* @test
   @bug 4276920
   @summary Tests that BasicComboPopup.hide() doesn't cause unnecessary repaints
   @key headful
*/

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class bug4276920 {

    static volatile TestComboBox combo;
    static volatile JFrame frame;

    public static void main(String[] args) throws Exception {
        try {
            SwingUtilities.invokeAndWait(bug4276920::createUI);
            Thread.sleep(2000);
            int before = combo.getRepaintCount();
            SwingUtilities.invokeAndWait(combo::hidePopup);
            int after = combo.getRepaintCount();
            if (after > before) {
                throw new Error("Failed 4276920: BasicComboPopup.hide() caused unnecessary repaint()");
            }
         } finally {
            if (frame != null) {
            SwingUtilities.invokeAndWait(frame::dispose);
            }
         }
     }

     static void createUI() {
        combo = new TestComboBox(new String[] {"Why am I so slow?"});
        frame = new JFrame("bug4276920");
        frame.getContentPane().add(combo);
        frame.pack();
        frame.validate();
        frame.setVisible(true);
    }

    static class TestComboBox extends JComboBox {
        int count = 0;

        TestComboBox(Object[] content) {
            super(content);
        }

        public void repaint() {
            super.repaint();
            count++;
        }

        int getRepaintCount() {
            return count;
        }
    }
}
