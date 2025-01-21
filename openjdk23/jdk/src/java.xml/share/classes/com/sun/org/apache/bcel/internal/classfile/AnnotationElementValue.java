/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.classfile;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @since 6.0
 */
public class AnnotationElementValue extends ElementValue {
    // For annotation element values, this is the annotation
    private final AnnotationEntry annotationEntry;

    public AnnotationElementValue(final int type, final AnnotationEntry annotationEntry, final ConstantPool cpool) {
        super(type, cpool);
        if (type != ANNOTATION) {
            throw new ClassFormatException("Only element values of type annotation can be built with this ctor - type specified: " + type);
        }
        this.annotationEntry = annotationEntry;
    }

    @Override
    public void dump(final DataOutputStream dos) throws IOException {
        dos.writeByte(super.getType()); // u1 type of value (ANNOTATION == '@')
        annotationEntry.dump(dos);
    }

    public AnnotationEntry getAnnotationEntry() {
        return annotationEntry;
    }

    @Override
    public String stringifyValue() {
        return annotationEntry.toString();
    }

    @Override
    public String toString() {
        return stringifyValue();
    }
}
