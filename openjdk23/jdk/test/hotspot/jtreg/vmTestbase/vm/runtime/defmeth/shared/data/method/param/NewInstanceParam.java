/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package vm.runtime.defmeth.shared.data.method.param;

import vm.runtime.defmeth.shared.data.Visitor;
import vm.runtime.defmeth.shared.data.Clazz;

/**
 * Represents a parameter value which is a fresh new instance of some
 * predefined type. Used to pass new instance of type {@code clz} as a parameter
 * to method calls.
 */
public class NewInstanceParam implements Param {
    Clazz clz;

    public NewInstanceParam(Clazz clz) {
        this.clz = clz;
    }

    public Clazz clazz() {
        return clz;
    }

    @Override
    public void visit(Visitor v) {
        v.visitParamNewInstance(this);
    }
}
