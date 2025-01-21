/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
   @test
   @bug 4130356
   @summary JTable.setRowSelectionInterval(int, int) shouldn't accept invalid range
*/

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class bug4130356 {

  public static void main(String[] argv) {
    JTable table = new JTable(4,3);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    try {
      table.setRowSelectionInterval(10,13);
      throw new Error("Invalid arguments supported!!!");
    } catch (IllegalArgumentException iae) {}
  }
}
