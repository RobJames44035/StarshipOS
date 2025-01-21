/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package java.lang.classfile;

import java.lang.classfile.attribute.CodeAttribute;
import java.lang.classfile.attribute.RecordComponentInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import jdk.internal.classfile.impl.AbstractUnboundModel;

import static java.util.Objects.requireNonNull;

/**
 * A {@link ClassFileElement} describing a {@code class} file structure that has
 * attributes, such as a {@code class} file, a field, a method, a {@link
 * CodeAttribute Code} attribute, or a record component.
 *
 * @jvms 4.7 Attributes
 * @sealedGraph
 * @since 24
 */
public sealed interface AttributedElement extends ClassFileElement
        permits ClassModel, CodeModel, FieldModel, MethodModel,
                RecordComponentInfo, AbstractUnboundModel {

    /**
     * {@return the attributes of this structure}
     */
    List<Attribute<?>> attributes();

    /**
     * Finds an attribute by name.  This is suitable to find attributes that
     * {@linkplain AttributeMapper#allowMultiple() allow at most one instance}
     * in one structure.  If this is used to find attributes that allow multiple
     * instances in one structure, the first matching instance is returned.
     *
     * @apiNote
     * This can easily find an attribute and send it to another {@link
     * ClassFileBuilder}, which is a {@code Consumer}:
     * {@snippet lang=java :
     * MethodModel method = null; // @replace substring=null; replacement=...
     * MethodBuilder mb = null; // @replace substring=null; replacement=...
     * method.findAttribute(Attributes.code()).ifPresent(mb);
     * }
     *
     * @param attr the attribute mapper
     * @param <T> the type of the attribute
     * @return the attribute, or {@code Optional.empty()} if the attribute
     * is not present
     */
    default <T extends Attribute<T>> Optional<T> findAttribute(AttributeMapper<T> attr) {
        requireNonNull(attr);
        for (Attribute<?> la : attributes()) {
            if (la.attributeMapper() == attr) {
                @SuppressWarnings("unchecked")
                var res = Optional.of((T) la);
                return res;
            }
        }
        return Optional.empty();
    }

    /**
     * Finds attributes by name.  This is suitable to find attributes that
     * {@linkplain AttributeMapper#allowMultiple() allow multiple instances}
     * in one structure.
     *
     * @param attr the attribute mapper
     * @param <T> the type of the attribute
     * @return the attributes, or an empty {@code List} if the attribute
     * is not present
     */
    default <T extends Attribute<T>> List<T> findAttributes(AttributeMapper<T> attr) {
        requireNonNull(attr);
        var list = new ArrayList<T>();
        for (var a : attributes()) {
            if (a.attributeMapper() == attr) {
                @SuppressWarnings("unchecked")
                T t = (T)a;
                list.add(t);
            }
        }
        return Collections.unmodifiableList(list);
    }
}
