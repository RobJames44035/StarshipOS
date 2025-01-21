/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package javax.lang.model.type;


/**
 * Represents an array type.
 * A multidimensional array type is represented as an array type
 * whose component type is also an array type.
 *
 * @since 1.6
 */
public interface ArrayType extends ReferenceType {

    /**
     * {@return the component type of this array type}
     */
    TypeMirror getComponentType();
}
