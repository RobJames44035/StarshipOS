/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4239714
 * @summary Tests that BasicMenuItemUI.installComponent() is protected
 */

import javax.swing.JMenuItem;
import javax.swing.plaf.basic.BasicMenuItemUI;

public class bug4239714 {
    public static void main(String[] argv) throws Exception {
        Tester tester = new Tester();
        tester.test();
    }

    static class Tester extends BasicMenuItemUI {
        public void test() {
            JMenuItem mi = new JMenuItem("bug4239714");
            installComponents(mi);
        }
    }
}
