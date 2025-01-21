/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy.drivers.lists;

import java.awt.Rectangle;
import java.awt.event.InputEvent;

import org.netbeans.jemmy.QueueTool;
import org.netbeans.jemmy.drivers.DriverManager;
import org.netbeans.jemmy.drivers.LightSupportiveDriver;
import org.netbeans.jemmy.drivers.MultiSelListDriver;
import org.netbeans.jemmy.operators.ComponentOperator;
import org.netbeans.jemmy.operators.JListOperator;
import org.netbeans.jemmy.operators.Operator;

/**
 * List driver for javax.swing.JList component type.
 *
 * @author Alexandre Iline(alexandre.iline@oracle.com)
 */
public class JListMouseDriver extends LightSupportiveDriver implements MultiSelListDriver {

    QueueTool queueTool;

    /**
     * Constructs a JListMouseDriver.
     */
    public JListMouseDriver() {
        super(new String[]{"org.netbeans.jemmy.operators.JListOperator"});
        queueTool = new QueueTool();
    }

    @Override
    public void selectItem(ComponentOperator oper, int index) {
        clickOnItem((JListOperator) oper, index);
    }

    @Override
    public void selectItems(ComponentOperator oper, int[] indices) {
        clickOnItem((JListOperator) oper, indices[0]);
        for (int i = 1; i < indices.length; i++) {
            clickOnItem((JListOperator) oper, indices[i], InputEvent.CTRL_MASK);
        }
    }

    /**
     * Clicks on a list item.
     *
     * @param oper an operator to click on.
     * @param index item index.
     */
    protected void clickOnItem(JListOperator oper, int index) {
        clickOnItem(oper, index, 0);
    }

    /**
     * Clicks on a list item.
     *
     * @param oper an operator to click on.
     * @param index item index.
     * @param modifiers a combination of {@code InputEvent.*_MASK} fields.
     */
    protected void clickOnItem(final JListOperator oper, final int index, final int modifiers) {
        if (!QueueTool.isDispatchThread()) {
            oper.scrollToItem(index);
        }
        queueTool.invokeSmoothly(new QueueTool.QueueAction<Void>("Path selecting") {
            @Override
            public Void launch() {
                Rectangle rect = oper.getCellBounds(index, index);
                DriverManager.getMouseDriver(oper).
                        clickMouse(oper,
                                rect.x + rect.width / 2,
                                rect.y + rect.height / 2,
                                1, Operator.getDefaultMouseButton(), modifiers,
                                oper.getTimeouts().create("ComponentOperator.MouseClickTimeout"));
                return null;
            }
        });
    }
}
