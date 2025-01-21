/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package java.lang.classfile.attribute;

import java.lang.classfile.constantpool.Utf8Entry;
import java.lang.classfile.instruction.LocalVariable;
import java.lang.constant.ClassDesc;

import jdk.internal.classfile.impl.BoundLocalVariable;
import jdk.internal.classfile.impl.UnboundAttribute;
import jdk.internal.classfile.impl.Util;

/**
 * Models a single local variable in the {@link LocalVariableTableAttribute}.
 *
 * @see LocalVariableTableAttribute#localVariables()
 * @see LocalVariable
 * @jvms 4.7.13 The {@code LocalVaribleTable} Attribute
 * @since 24
 */
public sealed interface LocalVariableInfo
        permits UnboundAttribute.UnboundLocalVariableInfo, BoundLocalVariable {

    /**
     * {@return the index into the code array, inclusive, at which the scope of
     * this variable begins}
     */
    int startPc();

    /**
     * {@return the length of the region of the code array in which this
     * variable is in scope}
     */
    int length();

    /**
     * {@return the name of the local variable}
     */
    Utf8Entry name();

    /**
     * {@return the field descriptor string of the local variable}
     */
    Utf8Entry type();

    /**
     * {@return the field descriptor of the local variable}
     */
    default ClassDesc typeSymbol() {
        return Util.fieldTypeSymbol(type());
    }

    /**
     * {@return the index into the local variable array of the current frame
     * which holds this local variable}
     */
    int slot();
}
