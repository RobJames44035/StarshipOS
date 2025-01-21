/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 4300224
 * @summary BasicListUI.ListDataHandler improperly updates list selection on insertion
 * @run main bug4300224
 */

import javax.swing.JList;
import javax.swing.DefaultListModel;

public class bug4300224 {

    public static void main(String[] args) throws Exception {
        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> list = new JList<>(model);

        model.addElement("List Item 1");
        model.addElement("List Item 2");
        model.addElement("List Item 3");
        model.addElement("List Item 4");
        list.setSelectedIndex(2);
        model.insertElementAt("Inserted Item", 0);
        if (list.getSelectedIndex() != 3) {
            throw new RuntimeException("Inserted element improperly updates list selection");
        }
    }
}
