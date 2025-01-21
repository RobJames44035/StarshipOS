/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/* @test
   @bug 6788484
   @summary NPE in DefaultTableCellHeaderRenderer.getColumnSortOrder() with null table
   @modules java.desktop/sun.swing.table
   @compile -XDignore.symbol.file=true bug6788484.java
   @author Alexander Potochkin
   @run main bug6788484
*/

/*
 * Compile with -XDignore.symbol.file=true option as a workaround for
 * specific behaviour described in 6380059 which restricts proprietary
 * package loading
 */

import sun.swing.table.DefaultTableCellHeaderRenderer;

import javax.swing.*;

public class bug6788484 {

    public static void main(String[] args) throws Exception {
        DefaultTableCellHeaderRenderer.getColumnSortOrder(null, 0);
    }
}
