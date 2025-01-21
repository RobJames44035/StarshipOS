/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package com.sun.org.apache.bcel.internal.generic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sun.org.apache.bcel.internal.Const;
import com.sun.org.apache.bcel.internal.classfile.AccessFlags;
import com.sun.org.apache.bcel.internal.classfile.Attribute;

/**
 * Super class for FieldGen and MethodGen objects, since they have some methods in common!
 *
 * @LastModified: May 2021
 */
public abstract class FieldGenOrMethodGen extends AccessFlags implements NamedAndTyped, Cloneable {

    /**
     * @deprecated (since 6.0) will be made private; do not access directly, use getter/setter
     */
    @Deprecated
    protected String name;

    /**
     * @deprecated (since 6.0) will be made private; do not access directly, use getter/setter
     */
    @Deprecated
    protected Type type;

    /**
     * @deprecated (since 6.0) will be made private; do not access directly, use getter/setter
     */
    @Deprecated
    protected ConstantPoolGen cp;

    private final List<Attribute> attributeList = new ArrayList<>();

    // @since 6.0
    private final List<AnnotationEntryGen> annotationList = new ArrayList<>();

    protected FieldGenOrMethodGen() {
    }

    /**
     * @since 6.0
     */
    protected FieldGenOrMethodGen(final int accessFlags) { // TODO could this be package protected?
        super(accessFlags);
    }

    protected void addAll(final Attribute[] attrs) {
        Collections.addAll(attributeList, attrs);
    }

    /**
     * @since 6.0
     */
    public void addAnnotationEntry(final AnnotationEntryGen ag) {
        annotationList.add(ag);
    }

    /**
     * Add an attribute to this method. Currently, the JVM knows about the 'Code', 'ConstantValue', 'Synthetic' and
     * 'Exceptions' attributes. Other attributes will be ignored by the JVM but do no harm.
     *
     * @param a attribute to be added
     */
    public void addAttribute(final Attribute a) {
        attributeList.add(a);
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (final CloneNotSupportedException e) {
            throw new Error("Clone Not Supported"); // never happens
        }
    }

    public AnnotationEntryGen[] getAnnotationEntries() {
        return annotationList.toArray(AnnotationEntryGen.EMPTY_ARRAY);
    }

    /**
     * @return all attributes of this method.
     */
    public Attribute[] getAttributes() {
        return attributeList.toArray(Attribute.EMPTY_ARRAY);
    }

    public ConstantPoolGen getConstantPool() {
        return cp;
    }

    /**
     * @return name of method/field.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @return signature of method/field.
     */
    public abstract String getSignature();

    @Override
    public Type getType() {
        return type;
    }

    /**
     * @since 6.0
     */
    public void removeAnnotationEntries() {
        annotationList.clear();
    }

    /**
     * @since 6.0
     */
    public void removeAnnotationEntry(final AnnotationEntryGen ag) {
        annotationList.remove(ag);
    }

    /**
     * Remove an attribute.
     */
    public void removeAttribute(final Attribute a) {
        attributeList.remove(a);
    }

    /**
     * Remove all attributes.
     */
    public void removeAttributes() {
        attributeList.clear();
    }

    public void setConstantPool(final ConstantPoolGen cp) { // TODO could be package-protected?
        this.cp = cp;
    }

    @Override
    public void setName(final String name) { // TODO could be package-protected?
        this.name = name;
    }

    @Override
    public void setType(final Type type) { // TODO could be package-protected?
        if (type.getType() == Const.T_ADDRESS) {
            throw new IllegalArgumentException("Type can not be " + type);
        }
        this.type = type;
    }
}
