/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy.util;

import org.netbeans.jemmy.operators.ComponentOperator;
import org.netbeans.jemmy.operators.Operator;
import org.netbeans.jemmy.operators.Operator.ComponentVisualizer;

/**
 *
 * Being used bas visualizer does nothing.
 *
 * @see
 * org.netbeans.jemmy.operators.Operator#setVisualizer(Operator.ComponentVisualizer)
 * @see org.netbeans.jemmy.operators.Operator.ComponentVisualizer
 *
 * @author Alexandre Iline (alexandre.iline@oracle.com)
 *
 */
public class EmptyVisualizer implements ComponentVisualizer {

    @Override
    public void makeVisible(ComponentOperator compOper) {
    }
}
