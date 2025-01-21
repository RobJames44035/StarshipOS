/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy.drivers.text;

import org.netbeans.jemmy.operators.ComponentOperator;
import org.netbeans.jemmy.operators.TextComponentOperator;

/**
 * TextDriver for AWT component types. Uses API calls.
 *
 * @author Alexandre Iline(alexandre.iline@oracle.com)
 */
public class AWTTextAPIDriver extends TextAPIDriver {

    /**
     * Constructs a AWTTextAPIDriver.
     */
    public AWTTextAPIDriver() {
        super(new String[]{"org.netbeans.jemmy.operators.TextComponentOperator"});
    }

    @Override
    public String getText(ComponentOperator oper) {
        return ((TextComponentOperator) oper).getText();
    }

    @Override
    public int getCaretPosition(ComponentOperator oper) {
        return ((TextComponentOperator) oper).getCaretPosition();
    }

    @Override
    public int getSelectionStart(ComponentOperator oper) {
        return ((TextComponentOperator) oper).getSelectionStart();
    }

    @Override
    public int getSelectionEnd(ComponentOperator oper) {
        return ((TextComponentOperator) oper).getSelectionEnd();
    }
}
