/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package java.lang.classfile.attribute;

import java.lang.classfile.Attribute;
import java.lang.classfile.AttributeMapper;
import java.lang.classfile.AttributeMapper.AttributeStability;
import java.lang.classfile.Attributes;
import java.lang.classfile.ClassFile;
import java.lang.classfile.MethodElement;
import java.lang.classfile.constantpool.ClassEntry;
import java.lang.constant.ClassDesc;
import java.lang.reflect.Executable;
import java.util.Arrays;
import java.util.List;

import jdk.internal.classfile.impl.BoundAttribute;
import jdk.internal.classfile.impl.UnboundAttribute;
import jdk.internal.classfile.impl.Util;

/**
 * Models the {@link Attributes#exceptions() Exceptions} attribute (JVMS {@jvms
 * 4.7.5}), which records the exceptions declared to be thrown by this
 * method.
 * <p>
 * This attribute only appears on methods, and does not permit {@linkplain
 * AttributeMapper#allowMultiple multiple instances} in a method.  It has a
 * data dependency on the {@linkplain AttributeStability#CP_REFS constant pool}.
 * <p>
 * The attribute was introduced in the Java Platform version 1.0.2, major
 * version {@value ClassFile#JAVA_1_VERSION}.
 *
 * @apiNote
 * Generic exceptions types thrown by a method and potentially annotated use of
 * those types are defined by {@link SignatureAttribute} and {@link
 * RuntimeVisibleTypeAnnotationsAttribute} respectively, which requires this
 * attribute to be present.
 *
 * @see Attributes#exceptions()
 * @jvms 4.7.5 The {@code Exceptions} Attribute
 * @since 24
 */
public sealed interface ExceptionsAttribute
        extends Attribute<ExceptionsAttribute>, MethodElement
        permits BoundAttribute.BoundExceptionsAttribute,
                UnboundAttribute.UnboundExceptionsAttribute {

    /**
     * {@return the exceptions declared to be thrown by this method}
     *
     * @see Executable#getExceptionTypes()
     */
    List<ClassEntry> exceptions();

    /**
     * {@return an {@code Exceptions} attribute}
     * @param exceptions the exceptions that may be thrown from this method
     */
    static ExceptionsAttribute of(List<ClassEntry> exceptions) {
        return new UnboundAttribute.UnboundExceptionsAttribute(exceptions);
    }

    /**
     * {@return an {@code Exceptions} attribute}
     * @param exceptions the exceptions that may be thrown from this method
     */
    static ExceptionsAttribute of(ClassEntry... exceptions) {
        return of(List.of(exceptions));
    }

    /**
     * {@return an {@code Exceptions} attribute}
     * @param exceptions the exceptions that may be thrown from this method
     */
    static ExceptionsAttribute ofSymbols(List<ClassDesc> exceptions) {
        return of(Util.entryList(exceptions));
    }

    /**
     * {@return an {@code Exceptions} attribute}
     * @param exceptions the exceptions that may be thrown from this method
     */
    static ExceptionsAttribute ofSymbols(ClassDesc... exceptions) {
        return ofSymbols(Arrays.asList(exceptions));
    }
}
