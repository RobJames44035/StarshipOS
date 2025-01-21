/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package jdk.test.lib.jittester;

import jdk.test.lib.jittester.visitors.Visitor;

/**
 * Type system's core..
 */
public abstract class Type extends IRNode implements Comparable<Type> {

    private final String typeName;

    protected Type(String typeName) {
        super(null);
        this.typeName = typeName;
    }

    @Override
    public Type getResultType() {
        return this;
    }

    @Override
    public boolean equals(Object t) {
        if (this == t) {
            return true;
        }
        if (t == null || !(t instanceof Type)) {
            return false;
        }
        return typeName.equals(((Type) t).typeName);
    }

    @Override
    public int compareTo(Type t) {
        return typeName.compareTo(t.typeName);
    }

    @Override
    public int hashCode() {
        return typeName.hashCode();
    }

    public abstract boolean canImplicitlyCastTo(Type t);

    public abstract boolean canExplicitlyCastTo(Type t);

    public abstract boolean canCompareTo(Type t);

    public abstract boolean canEquateTo(Type t);

    protected void exportSymbols() {
    }

    @Override
    public<T> T accept(Visitor<T> v) {
        return v.visit(this);
    }

    @Override
    public String getName() {
        return typeName;
    }
}
