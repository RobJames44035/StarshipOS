/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/* @test
   @bug 4171464
   @summary JComboBox should not throw InternalError
*/

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.event.ListDataListener;

public class bug4171464 {

    public static void main(String args[]) {
        ComboBoxModel model = new ComboBoxModel() {
            public void setSelectedItem(Object anItem) {}
            public Object getSelectedItem() {return null;}
            public int getSize() {return 0;}
            public Object getElementAt(int index) {return null;}
            public void addListDataListener(ListDataListener l) {}
            public void removeListDataListener(ListDataListener l) {}
        };
        JComboBox comboBox = new JComboBox();
        comboBox.setModel(model);
        try {
            comboBox.addItem(new Object() {});
        } catch (InternalError e) {
            // InternalError not suitable if app supplies non-mutable model.
            throw new RuntimeException("4171464 TEST FAILED");
        } catch (Exception e) {
            // Expected exception due to non-mutable model.
        }
    }
}
