/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package vm.runtime.defmeth.shared.data.method.result;

import vm.runtime.defmeth.shared.data.Clazz;
import vm.runtime.defmeth.shared.data.Visitor;

/**
 * Check that an exception of type {@code excCall} is thrown from a call.
 */
public class ThrowExResult implements Result {
    Clazz excClass;
    private String msg;
    private boolean isExact;

    public ThrowExResult(Clazz excClass, boolean isExact, String msg) {
        if ("java/lang/Throwable".equals(excClass.name())) {
            throw new IllegalArgumentException("Throwable isn't supported");
        }
        this.excClass = excClass;
        this.isExact = isExact;
    }

    public Clazz getExc() {
        return excClass;
    }

    public boolean isExact() {
        return isExact;
    }

    public boolean hasMessage() {
        return msg != null;
    }

    public String getMessage() {
        return msg;
    }

    @Override
    public void visit(Visitor v) {
        v.visitResultThrowExc(this);
    }
}
