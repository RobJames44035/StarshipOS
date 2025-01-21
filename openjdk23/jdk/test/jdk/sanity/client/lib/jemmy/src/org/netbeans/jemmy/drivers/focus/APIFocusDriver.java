/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy.drivers.focus;

import java.awt.event.FocusEvent;

import org.netbeans.jemmy.drivers.FocusDriver;
import org.netbeans.jemmy.drivers.LightSupportiveDriver;
import org.netbeans.jemmy.drivers.input.EventDriver;
import org.netbeans.jemmy.operators.ComponentOperator;

public class APIFocusDriver extends LightSupportiveDriver implements FocusDriver {

    EventDriver eDriver;

    public APIFocusDriver() {
        super(new String[]{"org.netbeans.jemmy.operators.ComponentOperator"});
        eDriver = new EventDriver();
    }

    @Override
    public void giveFocus(ComponentOperator operator) {
        operator.requestFocus();
        eDriver.dispatchEvent(operator.getSource(),
                new FocusEvent(operator.getSource(),
                        FocusEvent.FOCUS_GAINED));
    }
}
