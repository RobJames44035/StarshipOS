/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package java.lang.classfile.attribute;

import java.lang.classfile.Attribute;
import java.lang.classfile.AttributeMapper;
import java.lang.classfile.AttributeMapper.AttributeStability;
import java.lang.classfile.Attributes;
import java.lang.classfile.ClassElement;
import java.lang.classfile.ClassFile;
import java.lang.classfile.constantpool.ClassEntry;
import java.lang.constant.ClassDesc;

import jdk.internal.classfile.impl.BoundAttribute;
import jdk.internal.classfile.impl.TemporaryConstantPool;
import jdk.internal.classfile.impl.UnboundAttribute;

/**
 * Models the {@link Attributes#nestHost() NestHost} attribute (JVMS {@jvms
 * 4.7.28}), which indicates this class is a member of a nest and the host
 * class of the nest.
 * <p>
 * This attribute only appears on classes, and does not permit {@linkplain
 * AttributeMapper#allowMultiple multiple instances} in a class.  It has a
 * data dependency on the {@linkplain AttributeStability#CP_REFS constant pool}.
 * <p>
 * The attribute was introduced in the Java SE Platform version 11, major
 * version {@value ClassFile#JAVA_11_VERSION}.
 *
 * @see Attributes#nestHost()
 * @see NestMembersAttribute
 * @see Class#getNestHost()
 * @see Class#isNestmateOf(Class)
 * @jvms 4.7.28 The {@code NestHost} Attribute
 * @since 24
 */
public sealed interface NestHostAttribute extends Attribute<NestHostAttribute>, ClassElement
        permits BoundAttribute.BoundNestHostAttribute,
                UnboundAttribute.UnboundNestHostAttribute {

    /**
     * {@return the host class of the nest to which this class belongs}
     *
     * @see Class#getNestHost()
     */
    ClassEntry nestHost();

    /**
     * {@return a {@code NestHost} attribute}
     *
     * @param nestHost the host class of the nest
     */
    static NestHostAttribute of(ClassEntry nestHost) {
        return new UnboundAttribute.UnboundNestHostAttribute(nestHost);
    }

    /**
     * {@return a {@code NestHost} attribute}
     *
     * @param nestHost the host class of the nest
     * @throws IllegalArgumentException if {@code nestHost} represents a primitive type
     */
    static NestHostAttribute of(ClassDesc nestHost) {
        return of(TemporaryConstantPool.INSTANCE.classEntry(nestHost));
    }
}
