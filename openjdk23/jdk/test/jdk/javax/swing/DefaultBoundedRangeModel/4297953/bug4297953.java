/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4297953
 * @summary Tests that DefaultBoundedRangeModel doesn't zero out the
 *          extent value when maximum changes
 * @run main bug4297953
 */

import javax.swing.JScrollBar;

public class bug4297953 {
    public static void main(String[] args)  {
        JScrollBar sb = new JScrollBar(JScrollBar.HORIZONTAL, 90, 10, 0, 100);
        sb.setMaximum(80);
        if (sb.getVisibleAmount() != 10) {
            throw new RuntimeException("Failed: extent is " + sb.getVisibleAmount());
        }
    }
}
