/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package jdk.internal.classfile.impl;

import java.lang.classfile.AccessFlags;
import java.lang.classfile.Attribute;
import java.lang.classfile.ClassModel;
import java.lang.classfile.ClassReader;
import java.lang.classfile.FieldElement;
import java.lang.classfile.FieldModel;
import java.lang.classfile.constantpool.Utf8Entry;
import java.lang.reflect.AccessFlag;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public final class FieldImpl
        extends AbstractElement
        implements FieldModel, Util.Writable {

    private final ClassReader reader;
    private final int startPos, endPos, attributesPos;
    private List<Attribute<?>> attributes;

    public FieldImpl(ClassReader reader, int startPos, int endPos, int attributesPos) {
        this.reader = reader;
        this.startPos = startPos;
        this.endPos = endPos;
        this.attributesPos = attributesPos;
    }

    @Override
    public AccessFlags flags() {
        return new AccessFlagsImpl(AccessFlag.Location.FIELD, reader.readU2(startPos));
    }

    @Override
    public Optional<ClassModel> parent() {
        if (reader instanceof ClassReaderImpl cri)
            return Optional.of(cri.getContainedClass());
        else
            return Optional.empty();
    }

    @Override
    public Utf8Entry fieldName() {
        return reader.readEntry(startPos + 2, Utf8Entry.class);
    }

    @Override
    public Utf8Entry fieldType() {
        return reader.readEntry(startPos + 4, Utf8Entry.class);
    }

    @Override
    public List<Attribute<?>> attributes() {
        if (attributes == null) {
            attributes = BoundAttribute.readAttributes(this, reader, attributesPos, reader.customAttributes());
        }
        return attributes;
    }

    @Override
    public void writeTo(BufWriterImpl buf) {
        if (buf.canWriteDirect(reader)) {
            reader.copyBytesTo(buf, startPos, endPos - startPos);
        }
        else {
            buf.writeU2U2U2(flags().flagsMask(),
                    buf.cpIndex(fieldName()),
                    buf.cpIndex(fieldType()));
            Util.writeAttributes(buf, attributes());
        }
    }

    // FieldModel

    @Override
    public void writeTo(DirectClassBuilder builder) {
        if (builder.canWriteDirect(reader)) {
            builder.withField(this);
        }
        else {
            builder.withField(fieldName(), fieldType(), Util.writingAll(this));
        }
    }

    @Override
    public void forEach(Consumer<? super FieldElement> consumer) {
        consumer.accept(flags());
        for (Attribute<?> attr : attributes()) {
            if (attr instanceof FieldElement e)
                consumer.accept(e);
        }
    }

    @Override
    public String toString() {
        return String.format("FieldModel[fieldName=%s, fieldType=%s, flags=%d]",
                fieldName().stringValue(), fieldType().stringValue(), flags().flagsMask());
    }
}
