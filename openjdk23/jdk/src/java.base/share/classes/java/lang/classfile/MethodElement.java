/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package java.lang.classfile;

import java.lang.classfile.attribute.*;

/**
 * A marker interface for elements that can appear when traversing
 * a {@link MethodModel} or be presented to a {@link MethodBuilder}.
 *
 * @sealedGraph
 * @since 24
 */
public sealed interface MethodElement
        extends ClassFileElement
        permits AccessFlags, CodeModel, CustomAttribute,
                AnnotationDefaultAttribute, DeprecatedAttribute,
                ExceptionsAttribute, MethodParametersAttribute,
                RuntimeInvisibleAnnotationsAttribute, RuntimeInvisibleParameterAnnotationsAttribute,
                RuntimeInvisibleTypeAnnotationsAttribute, RuntimeVisibleAnnotationsAttribute,
                RuntimeVisibleParameterAnnotationsAttribute, RuntimeVisibleTypeAnnotationsAttribute,
                SignatureAttribute, SyntheticAttribute, UnknownAttribute {

}
