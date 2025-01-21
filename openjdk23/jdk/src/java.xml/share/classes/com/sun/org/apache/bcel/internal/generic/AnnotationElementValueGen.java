/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.generic;

import java.io.DataOutputStream;
import java.io.IOException;

import com.sun.org.apache.bcel.internal.classfile.AnnotationElementValue;
import com.sun.org.apache.bcel.internal.classfile.ElementValue;

/**
 * @since 6.0
 */
public class AnnotationElementValueGen extends ElementValueGen {
    // For annotation element values, this is the annotation
    private final AnnotationEntryGen a;

    public AnnotationElementValueGen(final AnnotationElementValue value, final ConstantPoolGen cpool, final boolean copyPoolEntries) {
        super(ANNOTATION, cpool);
        a = new AnnotationEntryGen(value.getAnnotationEntry(), cpool, copyPoolEntries);
    }

    public AnnotationElementValueGen(final AnnotationEntryGen a, final ConstantPoolGen cpool) {
        super(ANNOTATION, cpool);
        this.a = a;
    }

    public AnnotationElementValueGen(final int type, final AnnotationEntryGen annotation, final ConstantPoolGen cpool) {
        super(type, cpool);
        if (type != ANNOTATION) {
            throw new IllegalArgumentException("Only element values of type annotation can be built with this ctor - type specified: " + type);
        }
        this.a = annotation;
    }

    @Override
    public void dump(final DataOutputStream dos) throws IOException {
        dos.writeByte(super.getElementValueType()); // u1 type of value (ANNOTATION == '@')
        a.dump(dos);
    }

    public AnnotationEntryGen getAnnotation() {
        return a;
    }

    /**
     * Return immutable variant of this AnnotationElementValueGen
     */
    @Override
    public ElementValue getElementValue() {
        return new AnnotationElementValue(super.getElementValueType(), a.getAnnotation(), getConstantPool().getConstantPool());
    }

    @Override
    public String stringifyValue() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
