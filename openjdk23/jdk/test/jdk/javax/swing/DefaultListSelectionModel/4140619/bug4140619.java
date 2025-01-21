/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test
 * @bug 4140619
 * @summary Breaks SINGLE_SELECTION in DefaultListSelectionModel.setLeadSelectionIndex()
 * @run main bug4140619
 */

import javax.swing.DefaultListSelectionModel;
import javax.swing.ListSelectionModel;

public class bug4140619 {
    public static void main(String[] args) {
        DefaultListSelectionModel selection = new DefaultListSelectionModel();
        selection.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selection.setSelectionInterval(10, 10);
        selection.removeSelectionInterval(10, 10);
        selection.setLeadSelectionIndex(2);
        selection.setLeadSelectionIndex(30);
        selection.setLeadSelectionIndex(5);

        if (selection.getMinSelectionIndex()!=5
                || selection.getMaxSelectionIndex()!=5) {
            throw new RuntimeException("DefaultListSelectionModel: breaks SINGLE_SELECTION "
                    + "in setLeadSelectionIndex()");
        }
    }
}
