/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package vm.runtime.defmeth.shared.data.method;

import org.objectweb.asm.Opcodes;
import vm.runtime.defmeth.shared.Printer;
import vm.runtime.defmeth.shared.data.Visitor;

/**
 * Represents abstract method (method w/o code) in both concrete classes
 * and interfaces.
 */
public class AbstractMethod extends Method {

    public AbstractMethod(int acc, String name, String desc, String sig) {
        super(Opcodes.ACC_ABSTRACT | acc, name, desc, sig);
    }

    @Override
    public void visit(Visitor v) {
        v.visitAbstractMethod(this);
    }

    @Override
    public String toString() {
        return Printer.print(this);
    }
}
