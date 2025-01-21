/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
package com.sun.hotspot.igv.filter;

import com.sun.hotspot.igv.graph.Diagram;
import com.sun.hotspot.igv.graph.Figure;
import com.sun.hotspot.igv.graph.Selector;
import java.util.function.Function;
import java.util.List;

public class EditPropertyFilter extends AbstractFilter {

    private String name;
    private Selector selector;
    private final String[] inputPropertyNames;
    private final String outputPropertyName;
    private final Function<String[], String> editFunction;

    public EditPropertyFilter(String name, Selector selector,
                              String[] inputPropertyNames, String outputPropertyName,
                              Function<String[], String> editFunction) {
        this.name = name;
        this.selector = selector;
        this.inputPropertyNames = inputPropertyNames;
        this.outputPropertyName = outputPropertyName;
        this.editFunction = editFunction;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void apply(Diagram diagram) {
        List<Figure> list = selector.selected(diagram);
        String[] inputVals = new String[inputPropertyNames.length];
        for (Figure f : list) {
            for (int i = 0; i < inputPropertyNames.length; i++) {
                inputVals[i] = f.getProperties().get(inputPropertyNames[i]);
            }
            String outputVal = editFunction.apply(inputVals);
            f.getProperties().setProperty(outputPropertyName, outputVal);
        }
    }
}
