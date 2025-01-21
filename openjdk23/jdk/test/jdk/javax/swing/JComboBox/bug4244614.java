/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
   @test
   @bug 4244614
   @summary Tests that JComboBox has setAction(Action) constructor
*/

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Action;
import javax.swing.JComboBox;

public class bug4244614 {

/** Auxiliary class implementing Action
 */
    static class NullAction implements Action {
        public void addPropertyChangeListener(
                       PropertyChangeListener listener) {}
        public void removePropertyChangeListener(
                       PropertyChangeListener listener) {}
        public void putValue(String key, Object value) {}
        public void setEnabled(boolean b) {}
        public void actionPerformed(ActionEvent e) {}

        public Object getValue(String key) { return null; }
        public boolean isEnabled() { return false; }
    }

    public static void main(String[] argv) {
        Object[] comboData = {"First", "Second", "Third"};
        JComboBox combo = new JComboBox(comboData);
        Action action = new NullAction();
        combo.setAction(action);
    }
}
