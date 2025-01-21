/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4243927
 * @summary Tests that methods getAccessibleChild() and getAccessibleAt()
 *          of class JTableHeader.AccessibleJTableHeader do not throw NPE
 */

import javax.accessibility.AccessibleComponent;
import javax.accessibility.AccessibleContext;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

public class bug4243927 {
    public static void main(String[] argv) {
        JTableHeader header = new JTableHeader();
        header.getColumnModel().addColumn(new TableColumn(0));

        AccessibleContext c = header.getAccessibleContext();
        c.getAccessibleChild(0);
        ((AccessibleComponent)c).getAccessibleAt(new java.awt.Point(0,0));
    }
}
