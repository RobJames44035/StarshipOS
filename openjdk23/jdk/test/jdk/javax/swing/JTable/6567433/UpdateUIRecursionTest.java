/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * @test
 * @key headful
 * @bug 6567433
 *
 * @summary  JTable.updateUI() invokes updateUI() on its TableCellrenderer via
 * SwingUtilities.updateRendererOrEditorUI().
 * If the TableCellrenderer is a parent of this JTable the method recurses
 * endless.
 * This test tests that the fix is effective in avoiding recursion.
 *
 * @run main/othervm UpdateUIRecursionTest
 */


import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class UpdateUIRecursionTest extends JFrame implements TableCellRenderer {
    JTable table;
    DefaultTableCellRenderer renderer;

    public UpdateUIRecursionTest() {
        super("UpdateUIRecursionTest");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);

        String[] columnNames = {
                "First Name",
                "Last Name",
                "Sport",
                "# of Years",
                "Vegetarian"};

                Object[][] data = {
                    {"Mary", "Campione",
                    "Snowboarding", new Integer(5), new Boolean(false)},
                    {"Alison", "Huml",
                    "Rowing", new Integer(3), new Boolean(true)},
                    {"Kathy", "Walrath",
                    "Knitting", new Integer(2), new Boolean(false)},
                    {"Sharon", "Zakhour",
                    "Speed reading", new Integer(20), new Boolean(true)},
                    {"Philip", "Milne",
                    "Pool", new Integer(10), new Boolean(false)}
                };


        table = new JTable(data, columnNames);

        renderer = new DefaultTableCellRenderer();
        getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
        table.setDefaultRenderer(table.getColumnClass(1), this);

        setVisible(true);
    }

    public static void main(String[] args) throws Exception {

        SwingUtilities.invokeAndWait(new Runnable() {

            @Override
            public void run() {
                UpdateUIRecursionTest obj = new UpdateUIRecursionTest();

                obj.test();

                obj.disposeUI();
            }
        });
    }

    public void test() {
        table.updateUI();
    }

    public void disposeUI() {
        setVisible(false);
        dispose();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                         boolean isSelected, boolean hasFocus,
                                         int row, int column)
    {
         return renderer.getTableCellRendererComponent(table, value, isSelected,
                                                       hasFocus, row, column);
    }
}
