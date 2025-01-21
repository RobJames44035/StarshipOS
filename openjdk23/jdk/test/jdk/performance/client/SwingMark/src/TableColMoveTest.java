/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.SwingUtilities;

/**
  * This test is mean to isolate the speed of the JTable.
  * It creates a JTable and moves columns.
  */

public class TableColMoveTest extends AbstractSwingTest {
   JTable table;

   public JComponent getTestComponent() {
       JPanel panel = new JPanel();
       TableModel dataModel = new DefaultTableModel() {
         public int getColumnCount(){ return 25; }
         public int getRowCount() { return 20;}
         public Object getValueAt(int row, int col) { return Integer.valueOf(col) ;}
       };

       table = new JTable(dataModel);
       JScrollPane scrollPane = new JScrollPane(table);
       panel.add(scrollPane);
       return panel;
    }

    public String getTestName() {
       return "Table Column Move Test";
    }

    public void runTest() {
       testTable(table, 1);
    }

    public void testTable(JTable currentTable, int scrollBy) {

      TableColMover colmover = new TableColMover(currentTable);
      // Column Selection Test
      currentTable.clearSelection();

      for (int i = 0 ; i < currentTable.getColumnCount(); i++) {
        try {
          SwingUtilities.invokeAndWait(colmover);
        } catch (Exception e) {System.out.println(e);}
      }
   }

   public static void main(String[] args) {
      runStandAloneTest(new TableColMoveTest());
   }
}


class TableColMover implements Runnable {
    JTable table;

    public TableColMover(JTable table) {
        this.table = table;
    }

    public void run() {
       table.moveColumn(0, table.getColumnCount()-1 );
       Rectangle cellBound = table.getCellRect(0, table.getColumnCount()-1, true);
       table.scrollRectToVisible(cellBound);
       table.repaint();
    }
}
