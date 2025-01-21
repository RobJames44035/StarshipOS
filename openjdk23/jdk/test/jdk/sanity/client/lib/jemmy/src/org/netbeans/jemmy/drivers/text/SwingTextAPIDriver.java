/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy.drivers.text;

import org.netbeans.jemmy.operators.ComponentOperator;
import org.netbeans.jemmy.operators.JTextComponentOperator;

/**
 * TextDriver for swing component types. Uses API calls.
 *
 * @author Alexandre Iline(alexandre.iline@oracle.com)
 */
public class SwingTextAPIDriver extends TextAPIDriver {

    /**
     * Constructs a SwingTextAPIDriver.
     */
    public SwingTextAPIDriver() {
        super(new String[]{"org.netbeans.jemmy.operators.JTextComponentOperator"});
    }

    @Override
    public String getText(ComponentOperator oper) {
        return ((JTextComponentOperator) oper).getDisplayedText();
    }

    @Override
    public int getCaretPosition(ComponentOperator oper) {
        return ((JTextComponentOperator) oper).getCaretPosition();
    }

    @Override
    public int getSelectionStart(ComponentOperator oper) {
        return ((JTextComponentOperator) oper).getSelectionStart();
    }

    @Override
    public int getSelectionEnd(ComponentOperator oper) {
        return ((JTextComponentOperator) oper).getSelectionEnd();
    }
}
