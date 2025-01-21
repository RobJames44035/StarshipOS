/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package jdk.test.lib.jittester;

import jdk.test.lib.jittester.types.TypeKlass;


public class VariableInfo extends Symbol {

    public static final int LOCAL = 0x40;
    public static final int INITIALIZED = 0x80;

    protected VariableInfo() {
    }

    public VariableInfo(VariableInfo value) {
        super(value);
    }

    public VariableInfo(String name, TypeKlass owner, Type type, int flags) {
        super(name, owner, type, flags);
    }

    public VariableInfo(TypeKlass owner, Type type) {
        super("", owner, type, Symbol.NONE);
    }

    @Override
    protected Symbol copy() {
        return new VariableInfo(this);
    }

    @Override
    public Symbol deepCopy() {
        return new VariableInfo(this);
    }

    public boolean isLocal() {
        return (flags & LOCAL) != 0;
    }
}
