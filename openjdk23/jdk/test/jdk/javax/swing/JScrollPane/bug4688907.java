/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4688907
 * @summary ScrollPaneLayout.minimumLayoutSize incorrectly compares hsbPolicy
 */

import java.awt.Dimension;
import javax.swing.JScrollPane;

public class bug4688907 {
    public static void main(String[] args) throws Exception {
        JScrollPane sp = new JScrollPane();
        Dimension d1 = sp.getMinimumSize();
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        Dimension d2 = sp.getMinimumSize();
        if (d1.height == d2.height) {
            throw new RuntimeException("The scrollbar minimum size doesn't take " +
                    "into account horizontal scrollbar policy");
        }
    }
}
