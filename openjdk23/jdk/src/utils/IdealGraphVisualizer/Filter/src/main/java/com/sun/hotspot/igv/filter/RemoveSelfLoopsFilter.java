/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package com.sun.hotspot.igv.filter;

import com.sun.hotspot.igv.graph.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thomas Wuerthinger
 */
public class RemoveSelfLoopsFilter extends AbstractFilter {

    private String name;

    /** Creates a new instance of RemoveSelfLoops */
    public RemoveSelfLoopsFilter(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void apply(Diagram d) {

        for (Figure f : d.getFigures()) {

            for (InputSlot is : f.getInputSlots()) {

                List<FigureConnection> toRemove = new ArrayList<>();
                for (FigureConnection c : is.getConnections()) {

                    if (c.getOutputSlot().getFigure() == f) {
                        toRemove.add(c);
                    }
                }

                for (FigureConnection c : toRemove) {

                    c.remove();

                    OutputSlot os = c.getOutputSlot();
                    if (os.getConnections().size() == 0) {
                        f.removeSlot(os);
                    }

                    c.getInputSlot().setShortName("O");
                    c.getInputSlot().setText("Self Loop");
                }
            }
        }
    }
}
