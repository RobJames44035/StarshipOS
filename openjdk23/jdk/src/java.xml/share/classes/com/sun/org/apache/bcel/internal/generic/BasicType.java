/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.generic;

import com.sun.org.apache.bcel.internal.Const;

/**
 * Denotes basic type such as int.
 */
public final class BasicType extends Type {

    // @since 6.0 no longer final
    public static BasicType getType(final byte type) {
        switch (type) {
        case Const.T_VOID:
            return VOID;
        case Const.T_BOOLEAN:
            return BOOLEAN;
        case Const.T_BYTE:
            return BYTE;
        case Const.T_SHORT:
            return SHORT;
        case Const.T_CHAR:
            return CHAR;
        case Const.T_INT:
            return INT;
        case Const.T_LONG:
            return LONG;
        case Const.T_DOUBLE:
            return DOUBLE;
        case Const.T_FLOAT:
            return FLOAT;
        default:
            throw new ClassGenException("Invalid type: " + type);
        }
    }

    /**
     * Constructor for basic types such as int, long, 'void'
     *
     * @param type one of T_INT, T_BOOLEAN, ..., T_VOID
     * @see Const
     */
    BasicType(final byte type) {
        super(type, Const.getShortTypeName(type));
        if (type < Const.T_BOOLEAN || type > Const.T_VOID) {
            throw new ClassGenException("Invalid type: " + type);
        }
    }

    /**
     * @return true if both type objects refer to the same type
     */
    @Override
    public boolean equals(final Object type) {
        return type instanceof BasicType && ((BasicType) type).getType() == this.getType();
    }

    /**
     * @return a hash code value for the object.
     */
    @Override
    public int hashCode() {
        return super.getType();
    }
}
