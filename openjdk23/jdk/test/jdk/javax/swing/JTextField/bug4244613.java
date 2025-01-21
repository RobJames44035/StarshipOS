/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4244613
 * @summary Tests that JTextField has setAction(Action) constructor
 * @run main bug4244613
 */

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTextField;

public class bug4244613 {
    /** Auxilliary class implementing Action
    */
    static class NullAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {}
        public Object getValue(String key) { return null; }
        public boolean isEnabled() { return false; }
    }

    public static void main(String[] args) {
        JTextField tf = new JTextField("bug4244613");
        Action action = new NullAction();
        tf.setAction(action);
    }
}
