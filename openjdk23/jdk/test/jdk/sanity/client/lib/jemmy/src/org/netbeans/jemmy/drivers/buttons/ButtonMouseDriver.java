/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy.drivers.buttons;

import org.netbeans.jemmy.drivers.ButtonDriver;
import org.netbeans.jemmy.drivers.DriverManager;
import org.netbeans.jemmy.drivers.LightSupportiveDriver;
import org.netbeans.jemmy.drivers.MouseDriver;
import org.netbeans.jemmy.operators.ComponentOperator;
import org.netbeans.jemmy.operators.Operator;

/**
 * Driver to push a button by mouse click.
 *
 * @author Alexandre Iline(alexandre.iline@oracle.com)
 */
public class ButtonMouseDriver extends LightSupportiveDriver implements ButtonDriver {

    public ButtonMouseDriver() {
        super(new String[]{"org.netbeans.jemmy.operators.ComponentOperator"});
    }

    @Override
    public void press(ComponentOperator oper) {
        MouseDriver mdriver = DriverManager.getMouseDriver(oper);
        mdriver.moveMouse(oper,
                oper.getCenterXForClick(),
                oper.getCenterYForClick());
        mdriver.pressMouse(oper,
                oper.getCenterXForClick(),
                oper.getCenterYForClick(),
                Operator.getDefaultMouseButton(),
                0);
    }

    @Override
    public void release(ComponentOperator oper) {
        DriverManager.
                getMouseDriver(oper).
                releaseMouse(oper,
                        oper.getCenterXForClick(),
                        oper.getCenterYForClick(),
                        Operator.getDefaultMouseButton(),
                        0);
    }

    @Override
    public void push(ComponentOperator oper) {
        DriverManager.
                getMouseDriver(oper).
                clickMouse(oper,
                        oper.getCenterXForClick(),
                        oper.getCenterYForClick(),
                        1,
                        Operator.getDefaultMouseButton(),
                        0,
                        oper.getTimeouts().
                        create("ComponentOperator.MouseClickTimeout"));
    }
}
