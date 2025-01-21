/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package com.sun.hotspot.igv.filter;

import com.sun.hotspot.igv.graph.Diagram;
import com.sun.hotspot.igv.graph.Figure;
import com.sun.hotspot.igv.graph.Selector;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Thomas Wuerthinger
 */
public class RemoveFilter extends AbstractFilter {

    private List<RemoveRule> rules;
    private String name;

    public RemoveFilter(String name) {
        this.name = name;
        rules = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void apply(Diagram diagram) {
        for (RemoveRule r : rules) {
            List<Figure> selected = r.getSelector().selected(diagram);
            Set<Figure> toRemove = new HashSet<>(selected);

            if (r.getRemoveOrphans()) {
                boolean changed;
                do {
                    changed = false;
                    for (Figure f : diagram.getFigures()) {
                        if (!toRemove.contains(f)) {
                            if (toRemove.containsAll(f.getPredecessors()) && toRemove.containsAll(f.getSuccessors())) {
                                toRemove.add(f);
                                changed = true;
                            }
                        }
                    }
                } while (changed);
            }

            diagram.removeAllFigures(toRemove);
        }
    }

    public void addRule(RemoveRule rule) {
        rules.add(rule);
    }

    public static class RemoveRule {

        private Selector selector;
        private boolean removeOrphans;

        public RemoveRule(Selector selector) {
            this(selector, false);
        }

        public RemoveRule(Selector selector, boolean removeOrphans) {
            this.selector = selector;
            this.removeOrphans = removeOrphans;
        }

        public Selector getSelector() {
            return selector;
        }

        public boolean getRemoveOrphans() {
            return removeOrphans;
        }
    }
}
