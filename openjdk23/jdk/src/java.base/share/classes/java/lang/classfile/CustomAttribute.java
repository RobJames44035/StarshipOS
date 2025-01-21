/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package java.lang.classfile;

import java.lang.classfile.constantpool.PoolEntry;
import java.lang.classfile.constantpool.Utf8Entry;

import jdk.internal.classfile.impl.TemporaryConstantPool;

/**
 * Models a user-defined attribute in a {@code class} file.  API models for
 * user-defined attributes should extend this class.  A user-defined attribute
 * should also have an {@link AttributeMapper} defined, which will be returned
 * by {@link #attributeMapper}, and registered to the {@link
 * ClassFile.AttributeMapperOption} so the user-defined attributes can be read.
 * <p>
 * User-defined attributes are currently not delivered in the traversal of a
 * {@link CodeModel}.
 * <p>
 * Accessor methods on user-defined attributes read from {@code class} files
 * may throw {@link IllegalArgumentException} if the attribute model is lazily
 * evaluated, and the evaluation encounters malformed {@code class} file format
 * for the attribute.
 *
 * @param <T> the custom attribute type
 * @see java.lang.classfile.attribute
 * @since 24
 */
public abstract non-sealed class CustomAttribute<T extends CustomAttribute<T>>
        implements Attribute<T>, CodeElement, ClassElement, MethodElement, FieldElement {

    private final AttributeMapper<T> mapper;

    /**
     * Constructor for subclasses to call.
     *
     * @param mapper the attribute mapper
     */
    protected CustomAttribute(AttributeMapper<T> mapper) {
        this.mapper = mapper;
    }

    @Override
    public final AttributeMapper<T> attributeMapper() {
        return mapper;
    }

    /**
     * {@inheritDoc}
     *
     * @implSpec
     * The default implementation returns a {@code Utf8Entry} suitable for
     * writing only, which may be {@linkplain PoolEntry##unbound unbound}.
     * Subclasses representing attributes read from {@code class} files must
     * override this method.
     *
     * @see AttributeMapper#readAttribute
     */
    @Override
    public Utf8Entry attributeName() {
        return TemporaryConstantPool.INSTANCE.utf8Entry(mapper.name());
    }

    @Override
    public String toString() {
        return String.format("CustomAttribute[name=%s]", mapper.name());
    }
}
