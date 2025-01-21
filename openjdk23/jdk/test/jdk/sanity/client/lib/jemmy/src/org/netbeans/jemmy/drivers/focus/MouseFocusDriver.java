/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy.drivers.focus;

import org.netbeans.jemmy.QueueTool;
import org.netbeans.jemmy.drivers.DriverManager;
import org.netbeans.jemmy.drivers.FocusDriver;
import org.netbeans.jemmy.drivers.LightSupportiveDriver;
import org.netbeans.jemmy.operators.ComponentOperator;
import org.netbeans.jemmy.operators.Operator;

public class MouseFocusDriver extends LightSupportiveDriver implements FocusDriver {

    private QueueTool queueTool;

    public MouseFocusDriver() {
        super(new String[]{
            "org.netbeans.jemmy.operators.JListOperator",
            "org.netbeans.jemmy.operators.JScrollBarOperator",
            "org.netbeans.jemmy.operators.JSliderOperator",
            "org.netbeans.jemmy.operators.JTableOperator",
            "org.netbeans.jemmy.operators.JTextComponentOperator",
            "org.netbeans.jemmy.operators.JTreeOperator",
            "org.netbeans.jemmy.operators.ListOperator",
            "org.netbeans.jemmy.operators.ScrollbarOperator",
            "org.netbeans.jemmy.operators.TextAreaOperator",
            "org.netbeans.jemmy.operators.TextComponentOperator",
            "org.netbeans.jemmy.operators.TextFieldOperator"});
        queueTool = new QueueTool();
    }

    @Override
    public void giveFocus(final ComponentOperator oper) {
        if (!oper.hasFocus()) {
            queueTool.invokeSmoothly(new QueueTool.QueueAction<Void>("Mouse click to get focus") {
                @Override
                public Void launch() {
                    DriverManager.getMouseDriver(oper).
                            clickMouse(oper, oper.getCenterXForClick(), oper.getCenterYForClick(),
                                    1, Operator.getDefaultMouseButton(), 0,
                                    oper.getTimeouts().create("ComponentOperator.MouseClickTimeout"));
                    return null;
                }
            });
            oper.waitHasFocus();
        }
    }
}
