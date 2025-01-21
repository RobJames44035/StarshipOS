/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package java.lang.classfile.attribute;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.classfile.Annotation;
import java.lang.classfile.Attribute;
import java.lang.classfile.AttributeMapper;
import java.lang.classfile.AttributeMapper.AttributeStability;
import java.lang.classfile.Attributes;
import java.lang.classfile.ClassFile;
import java.lang.classfile.MethodElement;
import java.lang.reflect.AnnotatedElement;
import java.util.List;

import jdk.internal.classfile.impl.BoundAttribute;
import jdk.internal.classfile.impl.UnboundAttribute;

/**
 * Models the {@link Attributes#runtimeInvisibleParameterAnnotations()
 * RuntimeInvisibleParameterAnnotations} attribute (JVMS {@jvms 4.7.19}), which
 * stores declaration annotations on the method parameters of this method
 * that are visible to {@code class} file consumers but are not visible to
 * {@linkplain AnnotatedElement core reflection}.
 * <p>
 * This attribute only appears on methods, and does not permit {@linkplain
 * AttributeMapper#allowMultiple multiple instances} in a method.  It has a
 * data dependency on the {@linkplain AttributeStability#CP_REFS constant pool}.
 * <p>
 * The attribute was introduced in the Java SE Platform version 5.0, major
 * version {@value ClassFile#JAVA_5_VERSION}.
 *
 * @see Attributes#runtimeInvisibleParameterAnnotations()
 * @see ElementType#PARAMETER
 * @see RetentionPolicy#CLASS
 * @jvms 4.7.19 The {@code RuntimeInvisibleParameterAnnotations} Attribute
 * @since 24
 */
public sealed interface RuntimeInvisibleParameterAnnotationsAttribute
        extends Attribute<RuntimeInvisibleParameterAnnotationsAttribute>, MethodElement
        permits BoundAttribute.BoundRuntimeInvisibleParameterAnnotationsAttribute,
                UnboundAttribute.UnboundRuntimeInvisibleParameterAnnotationsAttribute {

    /**
     * {@return the list of run-time invisible annotations on the method parameters}
     * The element at the i'th index corresponds to the annotations on the i'th
     * formal parameter, but note that some synthetic or implicit parameters
     * may be omitted by this list.  If a parameter has no annotations, that
     * element is left empty, but is not omitted; thus, the list will never be
     * truncated because trailing parameters are not annotated.
     *
     * @see java.lang.reflect##LanguageJvmModel Java programming language and
     *      JVM modeling in core reflection
     */
    List<List<Annotation>> parameterAnnotations();

    /**
     * {@return a {@code RuntimeInvisibleParameterAnnotations} attribute}
     * The {@code parameterAnnotations} list should not be truncated, and must
     * have a length equal to the number of formal parameters; elements for
     * unannotated parameters may be empty, but may not be omitted.  It may omit
     * some synthetic or implicit parameters.
     *
     * @param parameterAnnotations a list of run-time invisible annotations for each parameter
     */
    static RuntimeInvisibleParameterAnnotationsAttribute of(List<List<Annotation>> parameterAnnotations) {
        return new UnboundAttribute.UnboundRuntimeInvisibleParameterAnnotationsAttribute(parameterAnnotations);
    }
}
