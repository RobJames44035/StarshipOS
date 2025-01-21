/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package java.lang.classfile.attribute;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.classfile.*;
import java.lang.classfile.AttributeMapper.AttributeStability;
import java.lang.reflect.AnnotatedType;
import java.util.List;

import jdk.internal.classfile.impl.BoundAttribute;
import jdk.internal.classfile.impl.UnboundAttribute;

/**
 * Models the {@link Attributes#runtimeVisibleTypeAnnotations()
 * RuntimeVisibleTypeAnnotations} attribute (JVMS {@jvms 4.7.20}), which
 * stores type-use annotations for the annotated uses of types in this
 * structure that are visible to both {@code class} file consumers and
 * {@linkplain AnnotatedType core reflection}.  Its delivery in the traversal of
 * a {@link CodeModel} may be toggled by {@link ClassFile.DebugElementsOption}.
 * <p>
 * This attribute appears on classes, fields, methods, {@code Code} attributes,
 * and record components, and does not permit {@linkplain
 * AttributeMapper#allowMultiple multiple instances} in one structure.  It has a
 * data dependency on {@linkplain AttributeStability#UNSTABLE arbitrary indices}
 * in the {@code class} file format, so users must take great care to ensure
 * this attribute is still correct after a {@code class} file has been transformed.
 * <p>
 * The attribute was introduced in the Java SE Platform version 8, major version
 * {@value ClassFile#JAVA_8_VERSION}.
 *
 * @see Attributes#runtimeVisibleTypeAnnotations()
 * @see java.compiler/javax.lang.model.type.TypeMirror
 * @see AnnotatedType
 * @see ElementType#TYPE_PARAMETER
 * @see ElementType#TYPE_USE
 * @see RetentionPolicy#RUNTIME
 * @jvms 4.7.20 the {@code RuntimeVisibleTypeAnnotations} Attribute
 * @since 24
 */
@SuppressWarnings("doclint:reference")
public sealed interface RuntimeVisibleTypeAnnotationsAttribute
        extends Attribute<RuntimeVisibleTypeAnnotationsAttribute>,
                ClassElement, MethodElement, FieldElement, CodeElement
        permits BoundAttribute.BoundRuntimeVisibleTypeAnnotationsAttribute,
                UnboundAttribute.UnboundRuntimeVisibleTypeAnnotationsAttribute {

    /**
     * {@return the run-time visible annotations on uses of types in this
     * structure}
     */
    List<TypeAnnotation> annotations();

    /**
     * {@return a {@code RuntimeVisibleTypeAnnotations} attribute}
     *
     * @param annotations the annotations
     */
    static RuntimeVisibleTypeAnnotationsAttribute of(List<TypeAnnotation> annotations) {
        return new UnboundAttribute.UnboundRuntimeVisibleTypeAnnotationsAttribute(annotations);
    }

    /**
     * {@return a {@code RuntimeVisibleTypeAnnotations} attribute}
     *
     * @param annotations the annotations
     */
    static RuntimeVisibleTypeAnnotationsAttribute of(TypeAnnotation... annotations) {
        return of(List.of(annotations));
    }
}
