/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package java.lang.classfile;

/**
 * Immutable model for a portion of (or the entirety of) a classfile.  Elements
 * that model parts of the classfile that have attributes will implement {@link
 * AttributedElement}; elements that model complex parts of the classfile that
 * themselves contain their own child elements will implement {@link
 * CompoundElement}.  Elements specific to various locations in the classfile
 * will implement {@link ClassElement}, {@link MethodElement}, etc.
 *
 * @sealedGraph
 * @since 24
 */
public sealed interface ClassFileElement
        permits AttributedElement, CompoundElement, Attribute,
                ClassElement, CodeElement, FieldElement, MethodElement {
}
