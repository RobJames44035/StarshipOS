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
import java.lang.classfile.constantpool.Utf8Entry;

import jdk.internal.classfile.impl.BoundAttribute;
import jdk.internal.classfile.impl.TemporaryConstantPool;
import jdk.internal.classfile.impl.UnboundAttribute;

/**
 * Models the {@link Attributes#sourceFile() SourceFile} attribute (JVMS {@jvms
 * 4.7.10}), which indicates the name of the source file from which this {@code
 * class} file was compiled.
 * <p>
 * This attribute only appears on classes, and does not permit {@linkplain
 * AttributeMapper#allowMultiple multiple instances} in a class.  It has a data
 * dependency on the {@linkplain AttributeStability#CP_REFS constant pool}.
 * <p>
 * The attribute was introduced in the Java SE Platform version 5.0, major
 * version {@value ClassFile#JAVA_5_VERSION}.
 *
 * @see Attributes#sourceFile()
 * @jvms 4.7.10 The {@code SourceFile} Attribute
 * @since 24
 */
public sealed interface SourceFileAttribute
        extends Attribute<SourceFileAttribute>, ClassElement
        permits BoundAttribute.BoundSourceFileAttribute, UnboundAttribute.UnboundSourceFileAttribute {

    /**
     * {@return the name of the source file from which this class was compiled}
     */
    Utf8Entry sourceFile();

    /**
     * {@return a source file attribute}
     *
     * @param sourceFile the source file name
     */
    static SourceFileAttribute of(String sourceFile) {
        return of(TemporaryConstantPool.INSTANCE.utf8Entry(sourceFile));
    }

    /**
     * {@return a source file attribute}
     *
     * @param sourceFile the source file name
     */
    static SourceFileAttribute of(Utf8Entry sourceFile) {
        return new UnboundAttribute.UnboundSourceFileAttribute(sourceFile);
    }
}
