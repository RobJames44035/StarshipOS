/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package vm.runtime.defmeth.shared.data;

import vm.runtime.defmeth.shared.data.method.Method;

/**
 * Represents Java interface class.
 */
public class InterfaceImpl extends ClazzImpl implements Interface {
    Interface[] parents; // superinterfaces

    public InterfaceImpl(int flags, String name, int ver, String sig, Interface[] parents, Method... methods) {
        super(name, ver, flags, sig, methods);
        this.parents = parents;
    }

    public Interface[] parents() {
        return parents;
    }

    @Override
    public void visit(Visitor v) {
        v.visitInterface(this);
    }
}
