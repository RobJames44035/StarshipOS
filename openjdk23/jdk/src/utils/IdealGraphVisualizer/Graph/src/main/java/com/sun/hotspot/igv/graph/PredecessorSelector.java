/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package com.sun.hotspot.igv.graph;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thomas Wuerthinger
 */
public class PredecessorSelector implements Selector {

    private Selector innerSelector;

    public PredecessorSelector(Selector innerSelector) {
        this.innerSelector = innerSelector;
    }

    @Override
    public List<Figure> selected(Diagram d) {
        List<Figure> inner = innerSelector.selected(d);
        List<Figure> result = new ArrayList<>();
        for (Figure f : d.getFigures()) {
            boolean saved = false;
            for (Figure f2 : f.getSuccessors()) {
                if (inner.contains(f2)) {
                    saved = true;
                }
            }

            if (saved) {
                result.add(f);
            }
        }

        return result;
    }
}
