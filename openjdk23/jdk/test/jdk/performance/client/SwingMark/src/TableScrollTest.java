/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.SwingUtilities;

public class TableScrollTest extends AbstractSwingTest {

   static JTable table;
   TableScroller tableScroller;
   JScrollPane scroller;
   static boolean backingStoreEnabled = true;
   static int rendererCount = 0;

   public JComponent getTestComponent() {

      TableModel model = new AbstractTableModel() {
         String[] data = { "1", "2", "3", "4", "5", "6",
            "8", "9", "10", "11" };

         public int getColumnCount() { return 10;}
         public int getRowCount() { return 1000;}
         public Object getValueAt(int row, int col) {
            return data[(row*col)%data.length];
         }
      };

      table = new CountTable(model);
      scroller = new JScrollPane(table);
      tableScroller = new TableScroller(table, 1);

      return scroller;
   }

   public String getTestName() {
      return "Table Scroll";
   }

   public void runTest() {
      for (int i = 0; i < 200; i++) {
         try {
            SwingUtilities.invokeLater( tableScroller );
            rest();
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
   }

   @SuppressWarnings("deprecation")
   public static void main(String[] args) {
       if (args.length > 0) {
          if (args[0].equals("-bs=off")) {
              backingStoreEnabled = false;
              System.out.println("BackingStore is off");
          }
       }

       runStandAloneTest(new TableScrollTest());
       System.out.println("Renderer painted :" + rendererCount);
       System.out.println( "Backing store is:" + ((JViewport)table.getParent()).isBackingStoreEnabled() );
   }

   class TableScroller implements Runnable {

      JTable table;
      int scrollAmount = 1;
      int currentVis = 10;

      public TableScroller(JTable tableToScroll, int scrollBy) {
         table =  tableToScroll;
         scrollAmount = scrollBy;
      }

      public void run() {
         int ensureToSeeRow = currentVis += scrollAmount;
         Rectangle cellBound = table.getCellRect(ensureToSeeRow, 0, true);
         table.scrollRectToVisible(cellBound);
      }
   }

   static class CountRenderer extends DefaultTableCellRenderer {

      public void paint(Graphics g) {
          super.paint(g);
          TableScrollTest.rendererCount++;
      }
   }

   class CountTable extends JTable {
      TableCellRenderer rend = new CountRenderer();

      public CountTable(TableModel tm) {
         super(tm);
      }

      public void paint(Graphics g) {
         super.paint(g);
         paintCount++;
      }

      public TableCellRenderer getCellRenderer(int row, int column) {
         return rend;
      }

      @SuppressWarnings("deprecation")
      public void addNotify() {
         super.addNotify();
         ((JViewport)getParent()).setBackingStoreEnabled(backingStoreEnabled);
      }
   }
}
