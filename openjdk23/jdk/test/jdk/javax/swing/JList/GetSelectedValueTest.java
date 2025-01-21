/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */
/*
 * @test
 * @key headful
 * @bug 7108280
 * @summary Verifies that getSelectedValue works fine without crash when
 *          the setSelectionInterval was called with indices outside the
 *          range of data present in DataModel
 * @run main GetSelectedValueTest
 */

import javax.swing.SwingUtilities;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import java.util.Objects;

public class GetSelectedValueTest {
    public static void main(String[] args) throws Exception {

        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                // Create a JList with 2 elements
                DefaultListModel dlm = new DefaultListModel();
                JList list = new JList<String>(dlm);
                list.setSelectionMode(
                        ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                dlm.addElement("1");
                dlm.addElement("2");

                // Set the selection interval from 0-2 (3 elements instead
                // of 2). The getSelectedValue should return the first
                // selected element
                list.setSelectionInterval(0, 2);
                checkSelectedIndex(list, "1");

                //here the smallest selection index is bigger than number of
                // elements in list. This should return null.
                list.setSelectionInterval(4, 5);
                checkSelectedIndex(list,null);
            }
        });
    }

    static void checkSelectedIndex(JList list, Object value)
            throws RuntimeException {
        Object selectedObject = list.getSelectedValue();
        if (!Objects.equals(value, selectedObject)) {
            System.out.println("Expected: " + value);
            System.out.println("Actual: " + selectedObject);
            throw new RuntimeException("Wrong selection");
        }
    }
}
