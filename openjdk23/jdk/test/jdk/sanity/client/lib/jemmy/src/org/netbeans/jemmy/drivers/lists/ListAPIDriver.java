/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy.drivers.lists;

import org.netbeans.jemmy.drivers.LightSupportiveDriver;
import org.netbeans.jemmy.drivers.MultiSelListDriver;
import org.netbeans.jemmy.operators.ComponentOperator;
import org.netbeans.jemmy.operators.ListOperator;

/**
 * List driver for java.awt.List component type. Uses API calls.
 *
 * @author Alexandre Iline(alexandre.iline@oracle.com)
 */
public class ListAPIDriver extends LightSupportiveDriver implements MultiSelListDriver {

    /**
     * Constructs a ListAPIDriver.
     */
    public ListAPIDriver() {
        super(new String[]{"org.netbeans.jemmy.operators.ListOperator"});
    }

    @Override
    public void selectItem(ComponentOperator oper, int index) {
        ListOperator loper = (ListOperator) oper;
        clearSelection(loper);
        loper.select(index);
    }

    @Override
    public void selectItems(ComponentOperator oper, int[] indices) {
        ListOperator loper = (ListOperator) oper;
        clearSelection(loper);
        for (int indice : indices) {
            loper.select(indice);
        }
    }

    private void clearSelection(ListOperator loper) {
        for (int i = 0; i < loper.getItemCount(); i++) {
            loper.deselect(i);
        }
    }
}
