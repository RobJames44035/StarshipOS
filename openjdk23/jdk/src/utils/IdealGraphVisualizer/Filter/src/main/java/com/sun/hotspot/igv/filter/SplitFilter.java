/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package com.sun.hotspot.igv.filter;

import com.sun.hotspot.igv.graph.*;
import java.util.List;

/**
 *
 * @author Thomas Wuerthinger
 */
public class SplitFilter extends AbstractFilter {

    private String name;
    private Selector selector;
    private String[] propertyNames;

    public SplitFilter(String name, Selector selector, String[] propertyNames) {
        this.name = name;
        this.selector = selector;
        this.propertyNames = propertyNames;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void apply(Diagram d) {
        List<Figure> list = selector.selected(d);

        for (Figure f : list) {
            String s = AbstractFilter.getFirstMatchingProperty(f, propertyNames);
            for (OutputSlot os : f.getOutputSlots()) {
                for (FigureConnection c : os.getConnections()) {
                    InputSlot is = c.getInputSlot();
                    if (f.getInputNode() != null) {
                        is.getSource().addSourceNode(f.getInputNode());
                        is.setColor(f.getColor());
                    }
                    if (s != null) {
                        is.setShortName(s);
                    }
                }
            }

            d.removeFigure(f);
        }
    }
}
