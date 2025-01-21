/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy.drivers.lists;

import org.netbeans.jemmy.QueueTool;
import org.netbeans.jemmy.drivers.LightSupportiveDriver;
import org.netbeans.jemmy.drivers.ListDriver;
import org.netbeans.jemmy.operators.ComponentOperator;
import org.netbeans.jemmy.operators.JTabbedPaneOperator;

/**
 * List driver for javax.swing.JTabbedPane component type.
 *
 * @author Alexandre Iline(alexandre.iline@oracle.com)
 */
public class JTabAPIDriver extends LightSupportiveDriver implements ListDriver {

    private QueueTool queueTool;

    /**
     * Constructs a JTabMouseDriver.
     */
    public JTabAPIDriver() {
        super(new String[]{"org.netbeans.jemmy.operators.JTabbedPaneOperator"});
        queueTool = new QueueTool();
    }

    @Override
    public void selectItem(final ComponentOperator oper, final int index) {
        if (index != -1) {
            queueTool.invokeSmoothly(new QueueTool.QueueAction<Void>("Selecting tab " + index + " by setting selectedIndex") {
                @Override
                public Void launch() {
                    ((JTabbedPaneOperator) oper).setSelectedIndex(index);
                    return null;
                }
            });
        }
    }
}
