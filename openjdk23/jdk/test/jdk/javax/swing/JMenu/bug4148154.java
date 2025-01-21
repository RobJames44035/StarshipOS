/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test
 * @bug 4148154
 * @summary Tests that menu items created by JMenu.add(Action) method
           have right HorizontalTextPosition.
 * @run main bug4148154
 */

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class bug4148154
{
    public static void main(String[] args) {
        JMenu menu = new JMenu();
        JMenuItem mi = menu.add(new AbstractAction() {
                public void actionPerformed(ActionEvent ev) {}
            });
        if (mi.getHorizontalTextPosition() != JMenu.LEADING &&
            mi.getHorizontalTextPosition() != JMenu.TRAILING) {

            throw new RuntimeException("Failed:");
        }
    }
}
