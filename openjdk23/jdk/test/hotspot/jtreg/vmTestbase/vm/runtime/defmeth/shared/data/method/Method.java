/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package vm.runtime.defmeth.shared.data.method;

import vm.runtime.defmeth.shared.data.Visitor;
import vm.runtime.defmeth.shared.data.Element;

/*
 * Represents a method w/o a link to any class.
 */
public class Method implements Element {
    protected int acc;
    protected String name;
    protected String desc;
    protected String sig;

    public Method(int acc, String name, String desc, String sig) {
        this.acc = acc;
        this.name = name;
        this.desc = desc;
        this.sig = sig;
    }

    public int acc() {
        return acc;
    }

    public String name() {
        return name;
    }

    public String desc() {
        return desc;
    }

    public String sig() {
        return sig;
    }

    public String[] getExceptions() {
        return new String[0]; // No exceptions supported yet
    }

    public boolean hasNonVoidReturn() {
        return !desc.matches(".*V");
    }

    public boolean isConstructor() {
        return name.equals("<init>") &&
               desc.equals("()V");
    }
    @Override
    public void visit(Visitor v) {
        v.visitMethod(this);
    }
}
