/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/* @test
 * @bug 8005019
 * @summary JTable passes row index instead of length when inserts selection interval
 * @author Alexander Scherbatiy
 * @run main bug8005019
 */

import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

public class bug8005019 {

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {

            @Override
            public void run() {
                testSelectionWithFilterTable();
            }
        });
    }

    private static void testSelectionWithFilterTable() {
        DefaultTableModel model = new DefaultTableModel(0, 1);
        // a model with 3 elements is the minimum to demonstrate
        // the bug
        int last = 2;
        for (int i = 0; i <= last; i++) {
            model.addRow(new Object[]{i});
        }
        JTable table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        // set selection at the end
        table.setRowSelectionInterval(last, last);
        // exclude rows based on identifier
        RowFilter filter = new GeneralFilter(new int[]{0});
        ((DefaultRowSorter) table.getRowSorter()).setRowFilter(filter);
        // insertRow _before or at_ selected model index, such that
        // endIndex (in event) > 1
        model.insertRow(2, new Object[]{"x"});
    }

    private static class GeneralFilter extends RowFilter<Object, Object> {

        private int[] columns;
        List excludes = Arrays.asList(0);

        GeneralFilter(int[] columns) {
            this.columns = columns;
        }

        public boolean include(RowFilter.Entry<? extends Object, ? extends Object> value) {
            int count = value.getValueCount();
            if (columns.length > 0) {
                for (int i = columns.length - 1; i >= 0; i--) {
                    int index = columns[i];
                    if (index < count) {
                        if (include(value, index)) {
                            return true;
                        }
                    }
                }
            } else {
                while (--count >= 0) {
                    if (include(value, count)) {
                        return true;
                    }
                }
            }
            return false;
        }

        protected boolean include(
                Entry<? extends Object, ? extends Object> entry,
                int index) {
            return !excludes.contains(entry.getIdentifier());
        }
    }
}
