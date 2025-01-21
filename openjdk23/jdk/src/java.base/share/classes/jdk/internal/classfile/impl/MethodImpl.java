/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package jdk.internal.classfile.impl;

import java.lang.classfile.*;
import java.lang.classfile.constantpool.Utf8Entry;
import java.lang.constant.MethodTypeDesc;
import java.lang.reflect.AccessFlag;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public final class MethodImpl
        extends AbstractElement
        implements MethodModel, MethodInfo, Util.Writable {

    private final ClassReader reader;
    private final int startPos, endPos, attributesPos;
    private List<Attribute<?>> attributes;
    private int[] parameterSlots;

    public MethodImpl(ClassReader reader, int startPos, int endPos, int attrStart) {
        this.reader = reader;
        this.startPos = startPos;
        this.endPos = endPos;
        this.attributesPos = attrStart;
    }

    @Override
    public AccessFlags flags() {
        return new AccessFlagsImpl(AccessFlag.Location.METHOD, reader.readU2(startPos));
    }

    @Override
    public Optional<ClassModel> parent() {
        if (reader instanceof ClassReaderImpl cri)
            return Optional.of(cri.getContainedClass());
        else
            return Optional.empty();
    }

    @Override
    public Utf8Entry methodName() {
        return reader.readEntry(startPos + 2, Utf8Entry.class);
    }

    @Override
    public Utf8Entry methodType() {
        return reader.readEntry(startPos + 4, Utf8Entry.class);
    }

    @Override
    public MethodTypeDesc methodTypeSymbol() {
        return Util.methodTypeSymbol(methodType());
    }

    @Override
    public int methodFlags() {
        return reader.readU2(startPos);
    }

    @Override
    public int parameterSlot(int paramNo) {
        if (parameterSlots == null)
            parameterSlots = Util.parseParameterSlots(methodFlags(), methodTypeSymbol());
        return parameterSlots[paramNo];
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
                    buf.cpIndex(methodName()),
                    buf.cpIndex(methodType()));
            Util.writeAttributes(buf, attributes());
        }
    }

    // MethodModel

    @Override
    public Optional<CodeModel> code() {
        return findAttribute(Attributes.code()).map(a -> (CodeModel) a);
    }

    @Override
    public void forEach(Consumer<? super MethodElement> consumer) {
        consumer.accept(flags());
        for (Attribute<?> attr : attributes()) {
            if (attr instanceof MethodElement e)
                consumer.accept(e);
        }
    }

    @Override
    public void writeTo(DirectClassBuilder builder) {
        if (builder.canWriteDirect(reader)) {
            builder.withMethod(this);
        }
        else {
            builder.withMethod(methodName(), methodType(), methodFlags(), Util.writingAll(this));
        }
    }

    @Override
    public String toString() {
        return String.format("MethodModel[methodName=%s, methodType=%s, flags=%d]",
                methodName().stringValue(), methodType().stringValue(), flags().flagsMask());
    }
}
