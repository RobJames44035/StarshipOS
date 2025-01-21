/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package com.sun.hotspot.igv.view.actions;

import java.awt.Point;
import java.awt.event.MouseEvent;
import org.netbeans.api.visual.action.SelectProvider;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author Tobias Holenstein
 */
public class CustomSelectAction extends WidgetAction.LockedAdapter {

    private final SelectProvider provider;

    public CustomSelectAction(SelectProvider provider) {
        this.provider = provider;
    }

    protected boolean isLocked() {
        return false;
    }

    protected int getModifierMask() {
        return org.openide.util.Utilities.isMac() ? MouseEvent.META_DOWN_MASK : MouseEvent.CTRL_DOWN_MASK;
    }

    @Override
    public State mousePressed(Widget widget, WidgetMouseEvent event) {
        Point localLocation = event.getPoint();
        if (event.getButton() == MouseEvent.BUTTON1 || event.getButton() == MouseEvent.BUTTON2) {
            boolean invertSelection = (event.getModifiersEx() & getModifierMask()) != 0;
            if (provider.isSelectionAllowed(widget, localLocation, invertSelection)) {
                provider.select(widget, localLocation, invertSelection);
                return State.CHAIN_ONLY;
            }
        }
        return State.REJECTED;
    }

}
