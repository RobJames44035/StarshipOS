/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package java.lang.classfile.attribute;

import java.lang.classfile.Attribute;
import java.lang.classfile.AttributeMapper;
import java.lang.classfile.AttributeMapper.AttributeStability;
import java.lang.classfile.Attributes;
import java.lang.classfile.ClassElement;
import java.lang.classfile.constantpool.Utf8Entry;

import jdk.internal.classfile.impl.BoundAttribute;
import jdk.internal.classfile.impl.TemporaryConstantPool;
import jdk.internal.classfile.impl.UnboundAttribute;

/**
 * Models the {@link Attributes#sourceId() SourceID} attribute, which records
 * the last modified time of the source file from which this {@code class} file
 * was compiled.
 * <p>
 * This attribute only appears on classes, and does not permit {@linkplain
 * AttributeMapper#allowMultiple multiple instances} in a class.  It has a
 * data dependency on the {@linkplain AttributeStability#CP_REFS constant pool}.
 * <p>
 * This attribute is not predefined in the Java SE Platform.  This is a
 * JDK-specific nonstandard attribute produced by the reference implementation
 * of the system Java compiler, defined by the {@code jdk.compiler} module.
 *
 * @see Attributes#sourceId()
 * @see CompilationIDAttribute
 * @see CharacterRangeTableAttribute
 * @since 24
 */
public sealed interface SourceIDAttribute
        extends Attribute<SourceIDAttribute>, ClassElement
        permits BoundAttribute.BoundSourceIDAttribute, UnboundAttribute.UnboundSourceIDAttribute {

    /**
     * {@return the source id}  The source id is the last modified time of the
     * source file (as reported by the file system, in milliseconds) when this
     * {@code class} file is compiled.
     */
    Utf8Entry sourceId();

    /**
     * {@return a {@code SourceID} attribute}
     *
     * @param sourceId the source id
     */
    static SourceIDAttribute of(Utf8Entry sourceId) {
        return new UnboundAttribute.UnboundSourceIDAttribute(sourceId);
    }

    /**
     * {@return a {@code SourceID} attribute}
     *
     * @param sourceId the source id
     */
    static SourceIDAttribute of(String sourceId) {
        return of(TemporaryConstantPool.INSTANCE.utf8Entry(sourceId));
    }
}
