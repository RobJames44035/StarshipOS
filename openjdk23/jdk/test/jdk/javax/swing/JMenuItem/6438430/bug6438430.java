/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 6438430
 * @summary Tests that submenu title doesn't overlap with submenu indicator
 *          in JPopupMenu
 * @author Mikhail Lapshin
 * @run main/othervm -Dswing.defaultlaf=javax.swing.plaf.metal.MetalLookAndFeel bug6438430
 * @run main/othervm -Dswing.defaultlaf=com.sun.java.swing.plaf.motif.MotifLookAndFeel bug6438430
 */

import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JCheckBoxMenuItem;

public class bug6438430 {
    public static void main(String[] args) {
        JMenu subMenu1 = new JMenu("Long-titled Sub Menu");
        subMenu1.add(new JMenuItem("SubMenu Item"));
        JMenuItem checkBoxMenuItem1 = new JCheckBoxMenuItem("CheckBox");

        JMenu menu1 = new JMenu("It works always");
        menu1.add(checkBoxMenuItem1);
        menu1.add(subMenu1);

        // Simulate DefaultMenuLayout calls.
        // The latest traversed menu item must be the widest.
        checkBoxMenuItem1.getPreferredSize();
        int width1 = subMenu1.getPreferredSize().width;
        System.out.println("width1 = " + width1);


        JMenu subMenu2 = new JMenu("Long-titled Sub Menu");
        subMenu2.add(new JMenuItem("SubMenu Item"));
        JMenuItem checkBoxMenuItem2 = new JCheckBoxMenuItem("CheckBox");

        JMenu menu2 = new JMenu("It did not work before the fix");
        menu2.add(subMenu2);
        menu2.add(checkBoxMenuItem2);

        // Simulate DefaultMenuLayout calls.
        // The latest traversed menu item must be the widest.
        subMenu2.getPreferredSize();
        int width2 = checkBoxMenuItem2.getPreferredSize().width;
        System.out.println("width2 = " + width2);

        if (width1 != width2) {
            throw new RuntimeException( "Submenu title and submenu indicator " +
                                        "overlap on JMenuItem!" );
        }

        System.out.println("Test passed");
    }
}
