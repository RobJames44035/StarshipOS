/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4143592
 * @summary Tests the method add(Component, int) of JMenu for insertion
            the given component to a specified position of menu
 * @run main bug4143592
 */

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class bug4143592 {

    public static void main(String[] argv) {
        JMenuBar mb = new JMenuBar();
        JMenu m = mb.add(new JMenu("Order"));
        m.add("beginning");
        m.add("middle");
        m.add("end");
        m.add(new JMenuItem("in between"), 1);
        if (!m.getItem(1).getText().equals("in between")) {
            throw new RuntimeException("Item was inserted incorrectly.");
        }
    }
}
