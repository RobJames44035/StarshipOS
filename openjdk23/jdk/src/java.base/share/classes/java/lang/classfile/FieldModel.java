/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package java.lang.classfile;

import java.lang.classfile.constantpool.Utf8Entry;
import java.lang.constant.ClassDesc;
import java.util.Optional;

import jdk.internal.classfile.impl.BufferedFieldBuilder;
import jdk.internal.classfile.impl.FieldImpl;
import jdk.internal.classfile.impl.Util;

/**
 * Models a field.  The contents of the field can be traversed via
 * a streaming view, or via random access (e.g.,
 * {@link #flags()}), or by freely mixing the two.
 *
 * @since 24
 */
public sealed interface FieldModel
        extends CompoundElement<FieldElement>, AttributedElement, ClassElement
        permits BufferedFieldBuilder.Model, FieldImpl {

    /** {@return the access flags} */
    AccessFlags flags();

    /** {@return the class model this field is a member of, if known} */
    Optional<ClassModel> parent();

    /** {@return the name of this field} */
    Utf8Entry fieldName();

    /** {@return the field descriptor of this field} */
    Utf8Entry fieldType();

    /** {@return the field descriptor of this field, as a symbolic descriptor} */
    default ClassDesc fieldTypeSymbol() {
        return Util.fieldTypeSymbol(fieldType());
    }
}
