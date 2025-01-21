/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package com.sun.hotspot.igv.filterwindow;

import org.openide.explorer.view.NodeListModel;
import org.openide.explorer.view.Visualizer;

/**
 *
 * @author Thomas Wuerthinger
 */
public class CheckNodeListModel extends NodeListModel {

    public CheckNode getCheckNodeAt(int index) {
        Object item = getElementAt(index);
        if (item != null) {
            return (CheckNode) Visualizer.findNode(item);
        }
        return null;
    }
}
