/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug 6795356
 * @summary Checks that SwingLazyValue class works correctly
 * @run main TableTest
 */

import java.awt.KeyboardFocusManager;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class TableTest {

    public static void main(String[] args) throws Exception {
        KeyboardFocusManager.getCurrentKeyboardFocusManager();

        JTable table = new JTable();
        TableCellEditor de = table.getDefaultEditor(Double.class);
        if (de == null) {
            throw new RuntimeException("Table default editor is null");
        }
    }
}
