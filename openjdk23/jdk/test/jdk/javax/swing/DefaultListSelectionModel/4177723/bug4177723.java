/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/* @test
 * @bug 4177723
 * @summary ListSelectionEvents fired on model changes affecting JList selection
 * @run main bug4177723
 */

import javax.swing.DefaultListModel;
import javax.swing.JList;

public class bug4177723 {
    static int count = 0;

    public static void main (String[] args) {
        DefaultListModel model = new DefaultListModel();
        for (int i = 0; i < 10; i++) {
            model.addElement("item " + i);
        }

        JList list = new JList(model);
        list.addListSelectionListener(e -> count++);

        list.getSelectionModel().setSelectionInterval(3, 8);
        model.removeRange(4, 7);
        if (count != 2) {
            throw new RuntimeException("ListSelectionEvent wasn't generated");
        }
    }
}
