/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy.drivers.lists;

import java.awt.Rectangle;

import javax.swing.JTabbedPane;

import org.netbeans.jemmy.QueueTool;
import org.netbeans.jemmy.drivers.DriverManager;
import org.netbeans.jemmy.drivers.LightSupportiveDriver;
import org.netbeans.jemmy.drivers.ListDriver;
import org.netbeans.jemmy.operators.ComponentOperator;
import org.netbeans.jemmy.operators.JTabbedPaneOperator;
import org.netbeans.jemmy.operators.Operator;

/**
 * List driver for javax.swing.JTabbedPane component type.
 *
 * @author Alexandre Iline(alexandre.iline@oracle.com)
 */
public class JTabMouseDriver extends LightSupportiveDriver implements ListDriver {

    private QueueTool queueTool;

    /**
     * Constructs a JTabMouseDriver.
     */
    public JTabMouseDriver() {
        super(new String[]{"org.netbeans.jemmy.operators.JTabbedPaneOperator"});
        queueTool = new QueueTool();
    }

    @Override
    public void selectItem(final ComponentOperator oper, final int index) {
        if (index != -1) {
            queueTool.invokeSmoothly(new QueueTool.QueueAction<Void>("Selecting tab " + index + " using mouse") {
                @Override
                public Void launch() {
                    Rectangle rect = ((JTabbedPaneOperator) oper).
                            getUI().
                            getTabBounds((JTabbedPane) oper.getSource(),
                                    index);
                    DriverManager.getMouseDriver(oper).
                            clickMouse(oper,
                                    (int) (rect.getX() + rect.getWidth() / 2),
                                    (int) (rect.getY() + rect.getHeight() / 2),
                                    1, Operator.getDefaultMouseButton(), 0,
                                    oper.getTimeouts().create("ComponentOperator.MouseClickTimeout"));
                    return null;
                }
            });
        }
    }
}
