/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4832765
 * @summary JList vertical scrolling doesn't work properly.
 * @run main bug4832765
 */

import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class bug4832765 {

    public static void main(String[] argv) throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            String[] data = {"One", "Two", "Three", "Four",
                    "Five", "Six ", "Seven", "Eight",
                    "Nine", "Ten", "Eleven", "Twelv"};
            JList<String> list = new JList<>(data);
            list.setLayoutOrientation(JList.HORIZONTAL_WRAP);

            JScrollPane jsp = new JScrollPane(list);
            Rectangle rect = list.getCellBounds(5, 5);
            Dimension d = new Dimension(200, rect.height);
            jsp.setPreferredSize(d);
            jsp.setMinimumSize(d);

            list.scrollRectToVisible(rect);

            int unit = list.getScrollableUnitIncrement(rect,
                    SwingConstants.VERTICAL,
                    -1);
            if (unit <= 0) {
                throw new RuntimeException("JList scrollable unit increment" +
                        " should be greate than 0.");
            }
        });
    }
}
