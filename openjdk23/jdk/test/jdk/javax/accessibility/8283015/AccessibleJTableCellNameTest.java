/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @key headful
 * @bug 4715496
 * @summary AccessibleJTableCell.getAccessible name incorrectly returns
 * cell instance string instead of cell text.
 * @run main AccessibleJTableCellNameTest
 */

import java.awt.Robot;

import javax.accessibility.Accessible;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

public class AccessibleJTableCellNameTest {
    private static JTable jTable;
    private static JFrame jFrame;
    private static volatile Accessible accessible;

    private static Object[][] rowData = {
        { "01", "02", "03", "04", "05" },
        { "11", "12", "13", "14", "15" },
        { "21", "22", "23", "24", "25" },
        { "31", "32", "33", "34", "35" },
        { "41", "42", "43", "44", "45" } };

    private static Object[] colNames = { "1", "2", "3", "4", "5" };

    private static void doTest() throws Exception {
        try {
            SwingUtilities.invokeAndWait(() -> createGUI());
            Robot robot = new Robot();
            robot.setAutoDelay(500);
            robot.waitForIdle();

            SwingUtilities.invokeAndWait(() -> {
                for (int i = 0; i <= colNames.length - 1; i++) {
                    Accessible accessible = jTable.getAccessibleContext().getAccessibleTable()
                        .getAccessibleColumnHeader().getAccessibleAt(0, i);

                    if (!(accessible.getAccessibleContext().getAccessibleName().equals(colNames[i]))) {
                        throw new RuntimeException(
                            "AccessibleJTableCell.getAccessibleName returns correct name for header cells");
                    }
                }
            });
        } finally {
            SwingUtilities.invokeAndWait(() -> jFrame.dispose());
        }
    }

    private static void createGUI() {
        jTable = new JTable(rowData, colNames);
        jFrame = new JFrame();
        jFrame.setBounds(100, 100, 300, 300);
        jFrame.getContentPane().add(jTable);
        jFrame.setVisible(true);
    }

    public static void main(String args[]) throws Exception {
        doTest();
        System.out.println("Test Passed");
    }
}

