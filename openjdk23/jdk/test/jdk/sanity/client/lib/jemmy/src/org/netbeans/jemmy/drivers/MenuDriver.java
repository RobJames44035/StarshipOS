/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy.drivers;

import org.netbeans.jemmy.operators.ComponentOperator;

/**
 * Defines how to work with menus.
 *
 * @author Alexandre Iline (alexandre.iline@oracle.com)
 */
public interface MenuDriver {

    /**
     * Pushes menu.
     *
     * @param oper Menu operator.
     * @param chooser Object defining menupath.
     * @return a result of menu pushing. It could be last pushed menuitem or
     * anything else.
     */
    public Object pushMenu(ComponentOperator oper, PathChooser chooser);
}
