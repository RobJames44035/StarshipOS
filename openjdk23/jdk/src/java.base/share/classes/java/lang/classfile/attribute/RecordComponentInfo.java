/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package java.lang.classfile.attribute;

import java.lang.classfile.Attribute;
import java.lang.classfile.AttributedElement;
import java.lang.classfile.constantpool.Utf8Entry;
import java.lang.constant.ClassDesc;
import java.lang.reflect.RecordComponent;
import java.util.List;

import jdk.internal.classfile.impl.BoundRecordComponentInfo;
import jdk.internal.classfile.impl.TemporaryConstantPool;
import jdk.internal.classfile.impl.UnboundAttribute;
import jdk.internal.classfile.impl.Util;

/**
 * Models a single record component in the {@link RecordAttribute}.
 * <p>
 * {@link SignatureAttribute}, {@link RuntimeVisibleAnnotationsAttribute},
 * {@link RuntimeInvisibleAnnotationsAttribute}, {@link
 * RuntimeVisibleTypeAnnotationsAttribute}, and {@link
 * RuntimeInvisibleTypeAnnotationsAttribute} are the only predefined attributes
 * that may exist on record components.
 *
 * @see RecordAttribute#components()
 * @see RecordComponent
 * @jvms 4.7.30 The {@code Record} Attribute
 * @since 24
 */
public sealed interface RecordComponentInfo
        extends AttributedElement
        permits BoundRecordComponentInfo, UnboundAttribute.UnboundRecordComponentInfo {
    /**
     * {@return the name of this component}
     *
     * @see RecordComponent#getName()
     */
    Utf8Entry name();

    /**
     * {@return the field descriptor string of this component}
     *
     * @apiNote
     * A record component may have a generic type; this information is stored
     * in the {@link SignatureAttribute Signature} attribute in this component.
     *
     * @see RecordComponent#getType()
     */
    Utf8Entry descriptor();

    /**
     * {@return the symbolic field descriptor of this component}
     *
     * @apiNote
     * A record component may have a generic type; this information is stored
     * in the {@link SignatureAttribute Signature} attribute in this component.
     *
     * @see RecordComponent#getType()
     */
    default ClassDesc descriptorSymbol() {
        return Util.fieldTypeSymbol(descriptor());
    }

    /**
     * {@return a record component description}
     *
     * @param name the component name
     * @param descriptor the component field descriptor string
     * @param attributes the component attributes
     */
    static RecordComponentInfo of(Utf8Entry name,
                                  Utf8Entry descriptor,
                                  List<Attribute<?>> attributes) {
        return new UnboundAttribute.UnboundRecordComponentInfo(name, descriptor, attributes);
    }

    /**
     * {@return a record component description}
     *
     * @param name the component name
     * @param descriptor the component field descriptor sting
     * @param attributes the component attributes
     */
    static RecordComponentInfo of(Utf8Entry name,
                                  Utf8Entry descriptor,
                                  Attribute<?>... attributes) {
        return of(name, descriptor, List.of(attributes));
    }

    /**
     * {@return a record component description}
     *
     * @param name the component name
     * @param descriptor the component symbolic field descriptor
     * @param attributes the component attributes
     */
    static RecordComponentInfo of(String name,
                                  ClassDesc descriptor,
                                  List<Attribute<?>> attributes) {
        return new UnboundAttribute.UnboundRecordComponentInfo(TemporaryConstantPool.INSTANCE.utf8Entry(name),
                                                               TemporaryConstantPool.INSTANCE.utf8Entry(descriptor),
                                                               attributes);
    }

    /**
     * {@return a record component description}
     *
     * @param name the component name
     * @param descriptor the component symbolic field descriptor
     * @param attributes the component attributes
     */
    static RecordComponentInfo of(String name,
                                  ClassDesc descriptor,
                                  Attribute<?>... attributes) {
        return of(name, descriptor, List.of(attributes));
    }
}
