/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * @test
 * @key headful
 * @bug 6567433
 *
 * @summary  JTableHeader.updateUI() invokes updateUI() on its TableCellrenderer via
 * SwingUtilities.updateComponentTreeUI().
 * If the Tablecellrenderer is a parent of this JTableHeader, the method recurses
 * endless.
 * This test tests that the fix is effective in avoiding recursion.
 *
 * @run main/othervm UpdateUIRecursionTest
 */

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

public class UpdateUIRecursionTest extends JFrame implements TableCellRenderer {
    JTable table;

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

        JTableHeader tableHeader =  new JTableHeader();
        table = new JTable(data, columnNames);
        table.setTableHeader(tableHeader);
        tableHeader.setDefaultRenderer(this);

        getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);

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
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void disposeUI() {
        setVisible(false);
        dispose();
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int col) {
        return new JLabel(String.valueOf(value));
    }
}


