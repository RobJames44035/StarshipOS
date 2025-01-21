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
public class AndSelector implements Selector {

    private Selector[] selectors;

    public AndSelector(Selector[] selectors) {
        this.selectors = selectors;
    }

    @Override
    public List<Figure> selected(Diagram d) {
        List<Figure> result = d.getFigures();
        for (Selector s : selectors) {
            List<Figure> selected = s.selected(d);
            List<Figure> newResult = new ArrayList<>();
            for (Figure f : result) {
                if (selected.contains(f)) {
                    newResult.add(f);
                }
            }
            result = newResult;
        }
        return result;
    }
}
