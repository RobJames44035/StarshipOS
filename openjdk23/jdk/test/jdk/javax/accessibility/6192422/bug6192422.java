/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

import javax.accessibility.Accessible;
import javax.accessibility.AccessibleContext;
import javax.accessibility.AccessibleRole;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;

/**
 * @test
 * @bug 6192422 7106851
 * @key headful
 * @summary Verifies fix for JMenuBar not being in the accessibility hierarchy
 */
public class bug6192422 {

    private static boolean foundJMenuBar = false;

    public static void main(String[] args) throws Throwable {
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                if (!testIt()) {
                    throw new RuntimeException("JMenuBar was not found");
                }
            }
        });
    }

    /*
     * Test whether JMenuBar is in accessibility hierarchy
     */
    private static boolean testIt() {

        JFrame frame = new JFrame("bug6192422");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /*
         * Add a menu bar to the frame using setJMenuBar. The setJMenuBar
         * method add the menu bar to the JLayeredPane.
         */
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(new JMenu("foo"));
        menuBar.add(new JMenu("bar"));
        menuBar.add(new JMenu("baz"));
        frame.setJMenuBar(menuBar);

        findJMenuBar(frame.getAccessibleContext());
        return foundJMenuBar;
    }

    /*
     * Finds the JMenuBar in the Accessibility hierarchy
     */
    private static void findJMenuBar(AccessibleContext ac) {
        if (ac != null) {
            System.err.println("findJMenuBar: ac = "+ac.getClass());
            int num = ac.getAccessibleChildrenCount();
            System.err.println("  #children "+num);

            for (int i = 0; i < num; i++) {
                System.err.println("  child #"+i);
                Accessible a = ac.getAccessibleChild(i);
                AccessibleContext child = a.getAccessibleContext();
                AccessibleRole role = child.getAccessibleRole();
                System.err.println("  role "+role);
                if (role == AccessibleRole.MENU_BAR) {
                    foundJMenuBar = true;
                    return;
                }
                if (child.getAccessibleChildrenCount() > 0) {
                    findJMenuBar(child);
                }
            }
        }
    }
}
