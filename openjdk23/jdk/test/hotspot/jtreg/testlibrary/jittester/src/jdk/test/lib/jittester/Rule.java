/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package jdk.test.lib.jittester;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;
import jdk.test.lib.jittester.factories.Factory;
import jdk.test.lib.jittester.utils.PseudoRandom;

/**
 * The Rule. A helper to perform production.
 */
public class Rule<T extends IRNode> extends Factory<T> implements Comparable<Rule<T>> {
    private final String name;
    private final TreeSet<RuleEntry> variants;
    private Integer limit = -1;

    @Override
    public int compareTo(Rule<T> rule) {
        return name.compareTo(rule.name);
    }

    public Rule(String name) {
        this.name = name;
        variants = new TreeSet<>();
    }

    public void add(String ruleName, Factory<? extends T> factory) {
        add(ruleName, factory, 1.0);
    }

    public void add(String ruleName, Factory<? extends T> factory, double weight) {
        variants.add(new RuleEntry(ruleName, factory, weight));
    }

    public int size() {
        return variants.size();
    }

    @Override
    public T produce() throws ProductionFailedException {
        if (!variants.isEmpty()) {
            // Begin production.
            LinkedList<RuleEntry> rulesList = new LinkedList<>(variants);
            PseudoRandom.shuffle(rulesList);

            while (!rulesList.isEmpty() && (limit == -1 || limit > 0)) {
                double sum = rulesList.stream()
                        .mapToDouble(r -> r.weight)
                        .sum();
                double rnd = PseudoRandom.random() * sum;
                Iterator<RuleEntry> iterator = rulesList.iterator();
                RuleEntry ruleEntry;
                double weightAccumulator = 0;
                do {
                    ruleEntry = iterator.next();
                    weightAccumulator += ruleEntry.weight;
                    if (weightAccumulator >= rnd) {
                        break;
                    }
                } while (iterator.hasNext());
                try {
                    return ruleEntry.produce();
                } catch (ProductionFailedException e) {
                }
                iterator.remove();
                if (limit != -1) {
                    limit--;
                }
            }
            //throw new ProductionFailedException();
        }
        // should probably throw exception here..
        //return getChildren().size() > 0 ? getChild(0).produce() : null;
        throw new ProductionFailedException();
    }

    private class RuleEntry extends Factory<T> implements Comparable<RuleEntry> {
        private final double weight;
        private final Factory<? extends T> factory;
        private final String name;

        private RuleEntry(String name, Factory<? extends T> factory, double weight) {
            this.name = name;
            this.weight = weight;
            this.factory = factory;
        }

        @Override
        public T produce() throws ProductionFailedException {
            return factory.produce();
        }

        @Override
        public int compareTo(RuleEntry entry) {
            return name.compareTo(entry.name);
        }
    }
}
