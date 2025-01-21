/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/* @test
 * @bug 4180943
 * @summary Extra borders created by DefaultListCellRenderer
 * @run main bug4180943
 */

import javax.swing.DefaultListCellRenderer;

public class bug4180943 {
    public static void main(String[] argv) {
        DefaultListCellRenderer lcr1 = new DefaultListCellRenderer();
        DefaultListCellRenderer lcr2 = new DefaultListCellRenderer();
        if (lcr1.getBorder() != lcr2.getBorder()) {
            throw new RuntimeException("Extra borders created by DefaultListCellRenderer");
        }
    }
}
