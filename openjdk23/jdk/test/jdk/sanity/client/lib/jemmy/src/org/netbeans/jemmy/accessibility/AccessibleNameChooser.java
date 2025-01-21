/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy.accessibility;

import javax.accessibility.AccessibleContext;

import org.netbeans.jemmy.operators.Operator;
import org.netbeans.jemmy.operators.Operator.StringComparator;

public class AccessibleNameChooser extends AccessibilityChooser {

    String name;
    StringComparator comparator;

    public AccessibleNameChooser(String name, StringComparator comparator) {
        this.name = name;
        this.comparator = comparator;
    }

    public AccessibleNameChooser(String name) {
        this(name, Operator.getDefaultStringComparator());
    }

    @Override
    public final boolean checkContext(AccessibleContext context) {
        return comparator.equals(context.getAccessibleName(), name);
    }

    @Override
    public String getDescription() {
        return "JComponent with \"" + name + "\" accessible name";
    }

    @Override
    public String toString() {
        return "AccessibleNameChooser{" + "name=" + name + ", comparator=" + comparator + '}';
    }
}
