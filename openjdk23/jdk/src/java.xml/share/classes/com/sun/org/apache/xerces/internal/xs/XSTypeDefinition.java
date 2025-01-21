/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.xs;

/**
 * This interface represents a complex or simple type definition.
 */
public interface XSTypeDefinition extends XSObject {
    /**
     * The object describes a complex type.
     */
    public static final short COMPLEX_TYPE              = 15;
    /**
     * The object describes a simple type.
     */
    public static final short SIMPLE_TYPE               = 16;
    /**
     * Return whether this type definition is a simple type or complex type.
     */
    public short getTypeCategory();

    /**
     * {base type definition}: either a simple type definition or a complex
     * type definition.
     */
    public XSTypeDefinition getBaseType();

    /**
     * {final}. For a complex type definition it is a subset of {extension,
     * restriction}. For a simple type definition it is a subset of
     * {extension, list, restriction, union}.
     * @param restriction  Extension, restriction, list, union constants
     *   (defined in <code>XSConstants</code>).
     * @return True if <code>restriction</code> is in the final set,
     *   otherwise false.
     */
    public boolean isFinal(short restriction);

    /**
     * For complex types the returned value is a bit combination of the subset
     * of {<code>DERIVATION_EXTENSION, DERIVATION_RESTRICTION</code>}
     * corresponding to <code>final</code> set of this type or
     * <code>DERIVATION_NONE</code>. For simple types the returned value is
     * a bit combination of the subset of {
     * <code>DERIVATION_RESTRICTION, DERIVATION_EXTENSION, DERIVATION_UNION, DERIVATION_LIST</code>
     * } corresponding to <code>final</code> set of this type or
     * <code>DERIVATION_NONE</code>.
     */
    public short getFinal();

    /**
     *  Convenience attribute. A boolean that specifies if the type definition
     * is anonymous.
     */
    public boolean getAnonymous();

    /**
     * Convenience method which checks if this type is derived from the given
     * <code>ancestorType</code>.
     * @param ancestorType  An ancestor type definition.
     * @param derivationMethod  A bit combination representing a subset of {
     *   <code>DERIVATION_RESTRICTION, DERIVATION_EXTENSION, DERIVATION_UNION, DERIVATION_LIST</code>
     *   }.
     * @return  True if this type is derived from <code>ancestorType</code>
     *   using only derivation methods from the <code>derivationMethod</code>
     *   .
     */
    public boolean derivedFromType(XSTypeDefinition ancestorType,
                                   short derivationMethod);

    /**
     * Convenience method which checks if this type is derived from the given
     * ancestor type.
     * @param namespace  An ancestor type namespace.
     * @param name  An ancestor type name.
     * @param derivationMethod  A bit combination representing a subset of {
     *   <code>DERIVATION_RESTRICTION, DERIVATION_EXTENSION, DERIVATION_UNION, DERIVATION_LIST</code>
     *   }.
     * @return  True if this type is derived from <code>ancestorType</code>
     *   using only derivation methods from the <code>derivationMethod</code>
     *   .
     */
    public boolean derivedFrom(String namespace,
                               String name,
                               short derivationMethod);

}
