/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy.drivers.text;

import org.netbeans.jemmy.operators.ComponentOperator;

class EndKey extends OffsetKey {

    TextKeyboardDriver cont;
    ComponentOperator oper;

    public EndKey(int keyCode, int mods, TextKeyboardDriver cont, ComponentOperator oper) {
        super(keyCode, mods);
        this.cont = cont;
        this.oper = oper;
    }

    @Override
    public int getDirection() {
        return 1;
    }

    @Override
    public int getExpectedPosition() {
        return cont.getText(oper).length();
    }
}
