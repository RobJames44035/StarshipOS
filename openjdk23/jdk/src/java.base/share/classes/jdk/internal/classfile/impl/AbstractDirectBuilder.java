/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package jdk.internal.classfile.impl;

import java.lang.classfile.Attribute;
import java.lang.classfile.constantpool.ConstantPool;

public class AbstractDirectBuilder<M> {
    protected final SplitConstantPool constantPool;
    protected final ClassFileImpl context;
    protected final AttributeHolder attributes = new AttributeHolder();
    protected M original;

    public AbstractDirectBuilder(SplitConstantPool constantPool, ClassFileImpl context) {
        this.constantPool = constantPool;
        this.context = context;
    }

    public SplitConstantPool constantPool() {
        return constantPool;
    }

    public boolean canWriteDirect(ConstantPool source) {
        return constantPool().canWriteDirect(source);
    }

    public void setOriginal(M original) {
        this.original = original;
    }

    public void writeAttribute(Attribute<?> a) {
        if (Util.isAttributeAllowed(a, context)) {
            attributes.withAttribute(a);
        }
    }
}
