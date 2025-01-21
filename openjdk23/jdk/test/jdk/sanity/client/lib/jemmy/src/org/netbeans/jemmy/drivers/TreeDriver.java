/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy.drivers;

import org.netbeans.jemmy.Timeout;
import org.netbeans.jemmy.operators.ComponentOperator;

/**
 * Defines how to work with trees.
 */
public interface TreeDriver extends MultiSelListDriver {

    /**
     * Expandes a node.
     *
     * @param oper Tree operator.
     * @param index Node index.
     */
    public void expandItem(ComponentOperator oper, int index);

    /**
     * Collapses a node.
     *
     * @param oper Tree operator.
     * @param index Node index.
     */
    public void collapseItem(ComponentOperator oper, int index);

    /**
     * Edits a node.
     *
     * @param oper Tree operator.
     * @param index Node index.
     * @param newValue New node value
     * @param waitEditorTime Time to wait node editor.
     */
    public void editItem(ComponentOperator oper, int index, Object newValue, Timeout waitEditorTime);

    /**
     * Starts node editing.
     *
     * @param oper Tree operator.
     * @param index Node index.
     * @param waitEditorTime Time to wait node editor.
     */
    public void startEditing(ComponentOperator oper, int index, Timeout waitEditorTime);
}
