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
public class RemoveInputsFilter extends AbstractFilter {

    private List<RemoveInputsRule> rules;
    private String name;

    public RemoveInputsFilter(String name) {
        this.name = name;
        rules = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void apply(Diagram diagram) {
        for (RemoveInputsRule r : rules) {
            List<Figure> list = r.getNodeSelector().selected(diagram);
            List<Figure> slotList = r.getSlotSelector().selected(diagram);
            for (Figure f : list) {
                for (InputSlot is : f.getInputSlots()) {
                    List<FigureConnection> conns = is.getConnections();
                    if (conns.size() == 1) {
                        Figure i = conns.get(0).getOutputSlot().getFigure();
                        if (slotList.contains(i)) {
                            is.removeAllConnections();
                        }
                    }
                }
            }
        }
    }

    public void addRule(RemoveInputsRule rule) {
        rules.add(rule);
    }

    public static class RemoveInputsRule {

        private Selector nodeSelector;
        private Selector slotSelector;

        public RemoveInputsRule(Selector nodeSelector, Selector slotSelector) {
            this.nodeSelector = nodeSelector;
            this.slotSelector = slotSelector;
        }

        public Selector getNodeSelector() {
            return nodeSelector;
        }

        public Selector getSlotSelector() {
            return slotSelector;
        }
    }
}
