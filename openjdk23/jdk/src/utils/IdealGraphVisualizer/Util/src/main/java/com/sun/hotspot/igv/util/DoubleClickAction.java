/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package com.sun.hotspot.igv.util;

import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author Thomas Wuerthinger
 */
public class DoubleClickAction extends WidgetAction.Adapter {

    private final DoubleClickHandler handler;

    public DoubleClickAction(DoubleClickHandler handler) {
        this.handler = handler;
    }

    @Override
    public WidgetAction.State mouseClicked(Widget widget, WidgetAction.WidgetMouseEvent event) {
        if (event.getClickCount() > 1) {
            handler.handleDoubleClick(widget, event);
            return WidgetAction.State.CONSUMED;
        }
        return WidgetAction.State.REJECTED;
    }
}
