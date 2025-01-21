/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy.drivers.tables;

import java.awt.Point;
import java.awt.event.KeyEvent;

import javax.swing.text.JTextComponent;

import org.netbeans.jemmy.QueueTool;
import org.netbeans.jemmy.drivers.DriverManager;
import org.netbeans.jemmy.drivers.LightSupportiveDriver;
import org.netbeans.jemmy.drivers.TableDriver;
import org.netbeans.jemmy.drivers.TextDriver;
import org.netbeans.jemmy.operators.ComponentOperator;
import org.netbeans.jemmy.operators.JTableOperator;
import org.netbeans.jemmy.operators.JTextComponentOperator;
import org.netbeans.jemmy.operators.Operator;

/**
 * TableDriver for javax.swing.JTableDriver component type.
 *
 * @author Alexandre Iline(alexandre.iline@oracle.com)
 */
public class JTableMouseDriver extends LightSupportiveDriver implements TableDriver {

    QueueTool queueTool;

    /**
     * Constructs a JTableMouseDriver.
     */
    public JTableMouseDriver() {
        super(new String[]{"org.netbeans.jemmy.operators.JTableOperator"});
        queueTool = new QueueTool();
    }

    @Override
    public void selectCell(ComponentOperator oper, int row, int column) {
        clickOnCell((JTableOperator) oper, row, column, 1);
    }

    @Override
    public void editCell(ComponentOperator oper, int row, int column, Object value) {
        JTableOperator toper = (JTableOperator) oper;
        toper.scrollToCell(row, column);
        if (!toper.isEditing()
                || toper.getEditingRow() != row
                || toper.getEditingColumn() != column) {
            clickOnCell((JTableOperator) oper, row, column, 2);
        }
        JTextComponentOperator textoper
                = new JTextComponentOperator((JTextComponent) toper.
                        waitSubComponent(new JTextComponentOperator.JTextComponentFinder()));
        TextDriver text = DriverManager.getTextDriver(JTextComponentOperator.class);
        text.clearText(textoper);
        text.typeText(textoper, value.toString(), 0);
        DriverManager.getKeyDriver(oper).
                pushKey(textoper, KeyEvent.VK_ENTER, 0,
                        oper.getTimeouts().
                        create("ComponentOperator.PushKeyTimeout"));
    }

    /**
     * Clicks on JTable cell.
     *
     * @param oper Table operator.
     * @param row Cell row index.
     * @param column Cell column index.
     * @param clickCount Count to click.
     */
    protected void clickOnCell(final JTableOperator oper, final int row, final int column, final int clickCount) {
        queueTool.invokeSmoothly(new QueueTool.QueueAction<Void>("Path selecting") {
            @Override
            public Void launch() {
                Point point = oper.getPointToClick(row, column);
                DriverManager.getMouseDriver(oper).
                        clickMouse(oper, point.x, point.y, clickCount,
                                Operator.getDefaultMouseButton(),
                                0,
                                oper.getTimeouts().create("ComponentOperator.MouseClickTimeout"));
                return null;
            }
        });
    }
}
