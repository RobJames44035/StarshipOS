/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/* @test
   @bug 4211454
   @summary Verifies ClassCastException in AbstractAction
   @run main bug4211454
*/

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

public class bug4211454 {

    public static void main(String[] args) {
        AbstractAction at = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Action!");
            }
        };
        for (int i = 0; i<9; i++) {
            at.putValue("key " + i, "name");
        }
        for (int i = 9; i>3; i--) {
            at.putValue("key " + i, null);
            at.putValue("Not a key " + i, null);
        }
    }
}
