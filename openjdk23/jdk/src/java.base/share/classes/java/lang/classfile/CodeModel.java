/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package java.lang.classfile;

import java.lang.classfile.attribute.CodeAttribute;
import java.lang.classfile.instruction.ExceptionCatch;
import java.util.List;
import java.util.Optional;

import jdk.internal.classfile.impl.BufferedCodeBuilder;

/**
 * Models the body of a method (the {@code Code} attribute).  The instructions
 * of the method body are accessed via a streaming view.
 *
 * @since 24
 */
public sealed interface CodeModel
        extends CompoundElement<CodeElement>, AttributedElement, MethodElement
        permits CodeAttribute, BufferedCodeBuilder.Model {

    /**
     * {@return the enclosing method, if known}
     */
    Optional<MethodModel> parent();

    /**
     * {@return the exception table of the method}  The exception table is also
     * modeled by {@link ExceptionCatch} elements in the streaming view.
     */
    List<ExceptionCatch> exceptionHandlers();
}
