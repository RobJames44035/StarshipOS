/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4244796
 * @summary Tests that JMenu has JMenu(Action) constructor
 * @run main bug4244796
 */

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Action;
import javax.swing.JMenu;

public class bug4244796 {

    /**
      * Auxilliary class implementing Action
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
        Action action = new NullAction();
        JMenu menu = new JMenu(action);
    }
}
