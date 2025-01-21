/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @key headful
 * @bug 4495286
 * @summary Verify that AccessibleJTable.setAccessibleSelction
 * selects rows/cols if getCellSelectionEnabled() is false
 * @run main AccessibleJTableSelectionTest
 */

import java.awt.BorderLayout;
import java.awt.Robot;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

public final class AccessibleJTableSelectionTest {

    private static JTable jTable;
    private static JFrame jFrame;

    private static Robot robot;

    private static void createGUI() {

        Object[][] rowData = { { "RowData1", Integer.valueOf(1) },
            { "RowData2", Integer.valueOf(2) },
            { "RowData3", Integer.valueOf(3) } };
        Object[] columnData = { "Column One", "Column Two" };

        jTable = new JTable(rowData, columnData);
        jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable.setRowSelectionAllowed(false);
        jTable.setColumnSelectionAllowed(false);
        jTable.setCellSelectionEnabled(true);

        jFrame = new JFrame();
        jFrame.add(new JScrollPane(jTable), BorderLayout.CENTER);
        jFrame.setSize(200, 200);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }

    private static void doTest() throws Exception {
        SwingUtilities.invokeAndWait(() -> createGUI());

        robot = new Robot();
        robot.waitForIdle();
        SwingUtilities.invokeAndWait(() -> {
            jTable.requestFocus();
            jTable.getAccessibleContext().getAccessibleSelection()
            .addAccessibleSelection(1);
        });

        robot.waitForIdle();
        SwingUtilities.invokeAndWait(() -> {
            if (!jTable.isRowSelected(0) || !jTable.isColumnSelected(1)) {
                throw new RuntimeException(
                    "Unexpected selection state of "
                    + "Table Row & Column");
            }
        });

        robot.waitForIdle();
        SwingUtilities.invokeAndWait(() -> {
            jTable.setRowSelectionAllowed(true);
            jTable.setColumnSelectionAllowed(false);
            jTable.setCellSelectionEnabled(false);
            jTable.requestFocus();
            jTable.getAccessibleContext().getAccessibleSelection()
            .addAccessibleSelection(3);
        });

        robot.waitForIdle();
        SwingUtilities.invokeAndWait(() -> {
            if (!jTable.isRowSelected(1)) {
                throw new RuntimeException(
                    "Unexpected selection state of "
                    + "Table Row & Column");
            }
        });

        robot.waitForIdle();
        SwingUtilities.invokeAndWait(() -> {
            jTable.setRowSelectionAllowed(false);
            jTable.setColumnSelectionAllowed(true);
            jTable.setCellSelectionEnabled(false);
            jTable.requestFocus();
            jTable.getAccessibleContext().getAccessibleSelection()
            .addAccessibleSelection(4);
        });

        robot.waitForIdle();
        SwingUtilities.invokeAndWait(() -> {
            if (!jTable.isColumnSelected(0)) {
                throw new RuntimeException(
                    "Unexpected selection state of "
                    + "Table Row & Column");
            }
        });

        robot.waitForIdle();
        SwingUtilities.invokeAndWait(() -> {
            jTable.setRowSelectionAllowed(true);
            jTable.setColumnSelectionAllowed(true);
            jTable.setCellSelectionEnabled(false);
            jTable.requestFocus();
            jTable.getAccessibleContext().getAccessibleSelection()
            .addAccessibleSelection(5);
        });

        robot.waitForIdle();
        SwingUtilities.invokeAndWait(() -> {
            if (!(jTable.isRowSelected(2) && jTable.isColumnSelected(1))) {
                throw new RuntimeException(
                    "Unexpected selection state of "
                    + "Table Row & Column");
            }
        });

        robot.waitForIdle();
        SwingUtilities.invokeAndWait(() -> {
            jTable.setCellSelectionEnabled(true);
            jTable.setColumnSelectionAllowed(true);
            jTable.setRowSelectionAllowed(true);
            jTable.requestFocus();
            jTable.getAccessibleContext().getAccessibleSelection()
            .addAccessibleSelection(4);
        });

        robot.waitForIdle();
        SwingUtilities.invokeAndWait(() -> {
            if (!(jTable.isRowSelected(2) && jTable.isColumnSelected(0)
                && jTable.isCellSelected(2, 0))) {
                throw new RuntimeException(
                    "Unexpected selection state of "
                    + "Table Row & Column");
            }
        });
    }

    public static void main(final String[] argv) throws Exception {
        doTest();
        SwingUtilities.invokeAndWait(() -> jFrame.dispose());
        System.out.println("Test Passed.");
    }
}

