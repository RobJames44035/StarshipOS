/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/* @test
   @bug 6442918 8005914
   @summary Ensures that empty table headers do not show "..."
   @author Shannon Hickey
   @library ../../regtesthelpers
   @build  Util
   @run main/manual bug6442918a
   @requires os.family == "windows"
*/

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;


public class bug6442918a {

    public static void main(String[] args) throws Throwable, Exception {
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf"
                                    + ".windows.WindowsLookAndFeel");
                } catch (Exception e) {
                    // test is for Windows look and feel
                    throw new RuntimeException("Test is only for WLaF."
                                   + e.getMessage());
                }
                runTest();
            }
        });
    }

    private static void runTest() {
        JDialog dialog = Util
                    .createModalDialogWithPassFailButtons("Empty header showing \"...\"");
        String[] columnNames = {"", "", "", "", "Testing"};
        String[][] data = {{"1", "2", "3", "4", "5"}};
        JTable table = new JTable(data, columnNames);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        int tableCellWidth = renderer.getFontMetrics(renderer.getFont())
                .stringWidth("test");
        table.setPreferredScrollableViewportSize(new Dimension(
                5 * tableCellWidth, 50));
        JPanel p = new JPanel();
        p.add(new JScrollPane(table));
        dialog.add(p, BorderLayout.NORTH);
        JTextArea area = new JTextArea();
        String txt  = "\nInstructions:\n\n";
               txt += "Only the last column header should show \"...\".";
        area.setText(txt);
        dialog.add(new JScrollPane(area), BorderLayout.CENTER);
        dialog.pack();
        dialog.setVisible(true);
    }
}
