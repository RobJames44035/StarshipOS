/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy.drivers.windows;

import java.awt.Window;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.WindowEvent;

import org.netbeans.jemmy.drivers.LightSupportiveDriver;
import org.netbeans.jemmy.drivers.WindowDriver;
import org.netbeans.jemmy.drivers.input.EventDriver;
import org.netbeans.jemmy.operators.ComponentOperator;
import org.netbeans.jemmy.operators.WindowOperator;

public class DefaultWindowDriver extends LightSupportiveDriver implements WindowDriver {

    EventDriver eDriver;

    public DefaultWindowDriver() {
        super(new String[]{"org.netbeans.jemmy.operators.WindowOperator"});
        eDriver = new EventDriver();
    }

    @Override
    public void activate(ComponentOperator oper) {
        checkSupported(oper);
        if (((WindowOperator) oper).getFocusOwner() == null) {
            ((WindowOperator) oper).toFront();
        }
        eDriver.dispatchEvent(oper.getSource(),
                new WindowEvent((Window) oper.getSource(),
                        WindowEvent.WINDOW_ACTIVATED));
        eDriver.dispatchEvent(oper.getSource(),
                new FocusEvent(oper.getSource(),
                        FocusEvent.FOCUS_GAINED));
    }

    @Override
    public void requestClose(ComponentOperator oper) {
        checkSupported(oper);
        eDriver.dispatchEvent(oper.getSource(),
                new WindowEvent((Window) oper.getSource(),
                        WindowEvent.WINDOW_CLOSING));
    }

    @Override
    public void requestCloseAndThenHide(ComponentOperator oper) {
        requestClose(oper);
        oper.setVisible(false);
    }

    @Override
    @Deprecated
    public void close(ComponentOperator oper) {
        requestClose(oper);
    }

    @Override
    public void move(ComponentOperator oper, int x, int y) {
        checkSupported(oper);
        oper.setLocation(x, y);
    }

    @Override
    public void resize(ComponentOperator oper, int width, int height) {
        checkSupported(oper);
        oper.setSize(width, height);
        eDriver.dispatchEvent(oper.getSource(),
                new ComponentEvent(oper.getSource(),
                        ComponentEvent.COMPONENT_RESIZED));
    }
}
