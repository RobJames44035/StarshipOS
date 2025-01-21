/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package jdk.test.lib.jittester;

import jdk.test.lib.jittester.types.TypeKlass;

public class Symbol {

    public String name;
    public Type type;
    public TypeKlass owner;
    public static final int NONE = 0x00;
    public static final int PRIVATE = 0x01;
    public static final int DEFAULT = 0x02;
    public static final int PROTECTED = 0x04;
    public static final int PUBLIC = 0x08;
    public static final int ACCESS_ATTRS_MASK = PRIVATE + PROTECTED + DEFAULT + PUBLIC;
    public static final int STATIC = 0x10;
    public static final int FINAL = 0x20;
    public int flags = NONE;

    protected Symbol() {
    }

    protected Symbol(String name) {
        this.name = name;
    }

    public Symbol(String name, TypeKlass owner, Type type, int flags) {
        this.name = name;
        this.owner = owner;
        this.type = type;
        this.flags = flags;
    }

    protected Symbol(Symbol value) {
        this.name = value.name;
        this.owner = value.owner;
        this.type = value.type;
        this.flags = value.flags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof Symbol)) {
            return false;
        }
        try {
            Symbol s = (Symbol) o;
            return owner.equals(s.owner) && name.equals(s.name);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }


    public boolean isStatic() {
        return (flags & STATIC) > 0;
    }

    public boolean isFinal() {
        return (flags & FINAL) > 0;
    }

    public boolean isPublic() {
        return (flags & PUBLIC) > 0;
    }

    public boolean isProtected() {
        return (flags & PROTECTED) > 0;
    }

    public boolean isPrivate() {
        return (flags & PRIVATE) > 0;
    }

    protected Symbol copy() {
        return new Symbol(this);
    }

    public Symbol deepCopy() {
        return new Symbol(this);
    }


    public TypeKlass getOwner() {
        return owner;
    }
}
