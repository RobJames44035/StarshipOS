/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */


import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 * AWT/Swing overlapping test for {@link javax.swing.JTable } component in GlassPane.
 * <p>See base class for details.
 */
/*
 * @test
 * @key headful
 * @summary Simple Overlapping test for JTable
 * @author sergey.grinev@oracle.com: area=awt.mixing
 * @library /java/awt/patchlib  ../../regtesthelpers
 * @modules java.desktop/sun.awt
 *          java.desktop/java.awt.peer
 * @build java.desktop/java.awt.Helper
 * @build Util
 * @run main JTableInGlassPaneOverlapping
 */
public class JTableInGlassPaneOverlapping extends GlassPaneOverlappingTestBase {

    {
        testResize = false;
    }

    @Override
    protected JComponent getSwingComponent() {
        // Create columns names
        String columnNames[] = {"Column 1", "Column 2", "Column 3"};

        // Create some data
        String dataValues[][] = {
            {"12", "234", "67"},
            {"-123", "43", "853"},
            {"93", "89.2", "109"},
            {"279", "9033", "3092"},
            {"12", "234", "67"},
            {"-123", "43", "853"},
            {"93", "89.2", "109"},
            {"279", "9033", "3092"},
            {"12", "234", "67"},
            {"-123", "43", "853"},
            {"93", "89.2", "109"},
            {"279", "9033", "3092"},
            {"12", "234", "67"},
            {"-123", "43", "853"},
            {"93", "89.2", "109"},
            {"279", "9033", "3092"}
        };

        // Create a new table instance
        JTable jt = new JTable(dataValues, columnNames);
        jt.getModel().addTableModelListener(new TableModelListener() {

            public void tableChanged(TableModelEvent e) {
                System.err.println("table changed");
            }
        });
        return jt;
    }

    // this strange plumbing stuff is required due to "Standard Test Machinery" in base class
    public static void main(String args[]) throws InterruptedException {
        instance = new JTableInGlassPaneOverlapping();
        OverlappingTestBase.doMain(args);
    }
}
