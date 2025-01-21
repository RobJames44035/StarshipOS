/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy.drivers.input;

import java.awt.AWTEvent;
import java.awt.Component;

import org.netbeans.jemmy.ComponentIsNotVisibleException;
import org.netbeans.jemmy.QueueTool;
import org.netbeans.jemmy.drivers.LightSupportiveDriver;

/**
 * Superclass for all drivers using event dispatching.
 *
 * @author Alexandre Iline(alexandre.iline@oracle.com)
 */
public class EventDriver extends LightSupportiveDriver {

    /**
     * Constructs an EventDriver object.
     *
     * @param supported an array of supported class names
     */
    public EventDriver(String[] supported) {
        super(supported);
    }

    /**
     * Constructs an EventDriver object suporting ComponentOperator.
     */
    public EventDriver() {
        this(new String[]{"org.netbeans.jemmy.operators.ComponentOperator"});
    }

    /**
     * Dispatches an event to the component.
     *
     * @param comp Component to dispatch events to.
     * @param event an event to dispatch.
     */
    public void dispatchEvent(Component comp, AWTEvent event) {
//        checkVisibility(comp);
        QueueTool.processEvent(event);
    }

    /**
     * Checks component visibility.
     *
     * @param component a component.
     */
    protected void checkVisibility(Component component) {
        if (!component.isVisible()) {
            throw (new ComponentIsNotVisibleException(component));
        }
    }

    /**
     * Class used fot execution of an event through the dispatching thread.
     */
    protected class Dispatcher extends QueueTool.QueueAction<Void> {

        AWTEvent event;
        Component component;

        /**
         * Constructs an EventDriver$Dispatcher object.
         *
         * @param component a component to dispatch event to.
         * @param e an event to dispatch.
         */
        public Dispatcher(Component component, AWTEvent e) {
            super(e.getClass().getName() + " event dispatching");
            this.component = component;
            event = e;
        }

        @Override
        public Void launch() {
            checkVisibility(component);
            component.dispatchEvent(event);
            return null;
        }
    }
}
