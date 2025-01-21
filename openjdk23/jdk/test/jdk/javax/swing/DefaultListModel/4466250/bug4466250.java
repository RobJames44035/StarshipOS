/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/* @test
 * @bug 4466250
 * @summary DefaultListModel.removeRange does not throw IllegalArgumentException
 * @run main bug4466250
*/

import javax.swing.DefaultListModel;
import javax.swing.JLabel;

public class bug4466250 {
    public static void main(String[] args) {
        DefaultListModel model = new DefaultListModel();
        int size = 16;
        for (int i = 0; i < size; i++ ) {
            model.addElement(new JLabel("wow"));
        }

        try {
            model.removeRange(3, 1);
            throw new RuntimeException("IllegalArgumentException has not been thrown");
        } catch (IllegalArgumentException e) {
        }
    }
}
