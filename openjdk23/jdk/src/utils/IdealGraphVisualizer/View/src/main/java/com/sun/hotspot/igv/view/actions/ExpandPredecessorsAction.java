/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package com.sun.hotspot.igv.view.actions;

import com.sun.hotspot.igv.graph.Figure;

/**
 *
 * @author Thomas Wuerthinger
 */
public final class ExpandPredecessorsAction extends ExpandAdjacentAction {

    @Override
    public void performAction() {
        expandFigures(Figure::getPredecessors);
    }

    @Override
    public String getName() {
        return "Expand Above";
    }
}
