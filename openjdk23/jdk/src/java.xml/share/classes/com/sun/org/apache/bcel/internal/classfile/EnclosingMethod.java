/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.classfile;

import java.io.DataInput;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sun.org.apache.bcel.internal.Const;
import com.sun.org.apache.bcel.internal.util.Args;

/**
 * This attribute exists for local or anonymous classes and ... there can be only one.
 *
 * @since 6.0
 */
public class EnclosingMethod extends Attribute {

    // Pointer to the CONSTANT_Class_info structure representing the
    // innermost class that encloses the declaration of the current class.
    private int classIndex;

    // If the current class is not immediately enclosed by a method or
    // constructor, then the value of the method_index item must be zero.
    // Otherwise, the value of the method_index item must point to a
    // CONSTANT_NameAndType_info structure representing the name and the
    // type of a method in the class referenced by the class we point
    // to in the class_index. *It is the compiler responsibility* to
    // ensure that the method identified by this index is the closest
    // lexically enclosing method that includes the local/anonymous class.
    private int methodIndex;

    // Ctors - and code to read an attribute in.
    EnclosingMethod(final int nameIndex, final int len, final DataInput input, final ConstantPool cpool) throws IOException {
        this(nameIndex, len, input.readUnsignedShort(), input.readUnsignedShort(), cpool);
    }

    private EnclosingMethod(final int nameIndex, final int len, final int classIndex, final int methodIndex, final ConstantPool cpool) {
        super(Const.ATTR_ENCLOSING_METHOD, nameIndex, Args.require(len, 4, "EnclosingMethod attribute length"), cpool);
        this.classIndex = Args.requireU2(classIndex, 0, cpool.getLength(), "EnclosingMethod class index");
        this.methodIndex = Args.requireU2(methodIndex, "EnclosingMethod method index");
    }

    @Override
    public void accept(final Visitor v) {
        v.visitEnclosingMethod(this);
    }

    @Override
    public Attribute copy(final ConstantPool constantPool) {
        return (Attribute) clone();
    }

    @Override
    public final void dump(final DataOutputStream file) throws IOException {
        super.dump(file);
        file.writeShort(classIndex);
        file.writeShort(methodIndex);
    }

    public final ConstantClass getEnclosingClass() {
        return super.getConstantPool().getConstant(classIndex, Const.CONSTANT_Class, ConstantClass.class);
    }

    // Accessors
    public final int getEnclosingClassIndex() {
        return classIndex;
    }

    public final ConstantNameAndType getEnclosingMethod() {
        if (methodIndex == 0) {
            return null;
        }
        return super.getConstantPool().getConstant(methodIndex, Const.CONSTANT_NameAndType, ConstantNameAndType.class);
    }

    public final int getEnclosingMethodIndex() {
        return methodIndex;
    }

    public final void setEnclosingClassIndex(final int idx) {
        classIndex = idx;
    }

    public final void setEnclosingMethodIndex(final int idx) {
        methodIndex = idx;
    }
}
