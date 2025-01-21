/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package jdk.internal.classfile.impl;

import java.lang.classfile.AccessFlags;
import java.lang.classfile.ClassModel;
import java.lang.classfile.FieldBuilder;
import java.lang.classfile.FieldElement;
import java.lang.classfile.FieldModel;
import java.lang.classfile.constantpool.ConstantPoolBuilder;
import java.lang.classfile.constantpool.Utf8Entry;
import java.lang.reflect.AccessFlag;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;

public final class BufferedFieldBuilder
        implements TerminalFieldBuilder {
    private final SplitConstantPool constantPool;
    private final ClassFileImpl context;
    private final Utf8Entry name;
    private final Utf8Entry desc;
    private final List<FieldElement> elements = new ArrayList<>();
    private AccessFlags flags;

    public BufferedFieldBuilder(SplitConstantPool constantPool,
                                ClassFileImpl context,
                                Utf8Entry name,
                                Utf8Entry type) {
        this.constantPool = constantPool;
        this.context = context;
        this.name = requireNonNull(name);
        this.desc = requireNonNull(type);
        this.flags = new AccessFlagsImpl(AccessFlag.Location.FIELD);
    }

    @Override
    public ConstantPoolBuilder constantPool() {
        return constantPool;
    }

    @Override
    public FieldBuilder with(FieldElement element) {
        elements.add(requireNonNull(element));
        if (element instanceof AccessFlags f) this.flags = f;
        return this;
    }

    public BufferedFieldBuilder run(Consumer<? super FieldBuilder> handler) {
        handler.accept(this);
        return this;
    }

    public FieldModel toModel() {
        return new Model();
    }

    public final class Model
            extends AbstractUnboundModel<FieldElement>
            implements FieldModel {
        public Model() {
            super(BufferedFieldBuilder.this.elements);
        }

        @Override
        public Optional<ClassModel> parent() {
            return Optional.empty();
        }

        @Override
        public AccessFlags flags() {
            return flags;
        }

        @Override
        public Utf8Entry fieldName() {
            return name;
        }

        @Override
        public Utf8Entry fieldType() {
            return desc;
        }

        @Override
        public void writeTo(DirectClassBuilder builder) {
            builder.withField(name, desc, Util.writingAll(this));
        }

        @Override
        public String toString() {
            return String.format("FieldModel[fieldName=%s, fieldType=%s, flags=%d]", name.stringValue(), desc.stringValue(), flags.flagsMask());
        }
    }
}
