/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 4962718
 * @summary Propertychange Listener not fired by inheritPopupMenu and Popupmenu properties
 * @key headful
 * @run main bug4962718
*/

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class bug4962718 {
    static volatile boolean popupWasSet = false;
    static volatile boolean inheritWasSet = false;
    static JFrame frame;

    public static void main(String[] args) throws Exception {
        try {
            SwingUtilities.invokeAndWait(() -> {
                frame = new JFrame("bug4962718");
                JButton button = new JButton("For test");
                JPopupMenu popup = new JPopupMenu();

                button.addPropertyChangeListener(new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent evt) {
                        if (evt.getPropertyName().equals("inheritsPopupMenu")) {
                            inheritWasSet = true;
                        } else if( evt.getPropertyName().
                                  equals("componentPopupMenu")) {
                            popupWasSet = true;
                        }
                    }
                });

                frame.add(button);
                button.setInheritsPopupMenu(true);
                button.setInheritsPopupMenu(false);
                button.setComponentPopupMenu(popup);
                button.setComponentPopupMenu(null);
                frame.pack();
                frame.setVisible(true);
            });

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}

            if (!inheritWasSet) {
                throw new RuntimeException("Test failed, inheritsPopupMenu " +
                                   " property change listener was not called");
            }
            if (!popupWasSet) {
                throw new RuntimeException("Test failed, componentPopupMenu " +
                                    " property change listener was not called");
            }
        } finally {
            SwingUtilities.invokeAndWait(() -> {
                if (frame != null) {
                    frame.dispose();
                }
            });
        }
    }
}

