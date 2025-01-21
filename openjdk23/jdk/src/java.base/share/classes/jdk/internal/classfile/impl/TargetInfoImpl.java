/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package jdk.internal.classfile.impl;

import java.lang.classfile.Label;
import java.lang.classfile.TypeAnnotation.*;
import java.util.List;

import static java.util.Objects.requireNonNull;

public final class TargetInfoImpl {

    private TargetInfoImpl() {
    }

    private static TargetType checkValid(TargetType targetType, int rangeFrom, int rangeTo) {
        if (targetType.targetTypeValue() < rangeFrom || targetType.targetTypeValue() > rangeTo)
            throw new IllegalArgumentException("Wrong target type specified " + targetType);
        return targetType;
    }

    public record TypeParameterTargetImpl(TargetType targetType, int typeParameterIndex)
            implements TypeParameterTarget {

        public TypeParameterTargetImpl(TargetType targetType, int typeParameterIndex) {
            this.targetType = checkValid(targetType, TARGET_CLASS_TYPE_PARAMETER, TARGET_METHOD_TYPE_PARAMETER);
            this.typeParameterIndex = typeParameterIndex;
        }
    }

    public record SupertypeTargetImpl(int supertypeIndex) implements SupertypeTarget {
        @Override
        public TargetType targetType() {
            return TargetType.CLASS_EXTENDS;
        }
    }

    public record TypeParameterBoundTargetImpl(TargetType targetType, int typeParameterIndex, int boundIndex)
            implements TypeParameterBoundTarget {

        public TypeParameterBoundTargetImpl(TargetType targetType, int typeParameterIndex, int boundIndex) {
            this.targetType = checkValid(targetType, TARGET_CLASS_TYPE_PARAMETER_BOUND, TARGET_METHOD_TYPE_PARAMETER_BOUND);
            this.typeParameterIndex = typeParameterIndex;
            this.boundIndex = boundIndex;
        }
    }

    public record EmptyTargetImpl(TargetType targetType) implements EmptyTarget {

        public EmptyTargetImpl(TargetType targetType) {
            this.targetType = checkValid(targetType, TARGET_FIELD, TARGET_METHOD_RECEIVER);
        }
    }

    public record FormalParameterTargetImpl(int formalParameterIndex) implements FormalParameterTarget {
        @Override
        public TargetType targetType() {
            return TargetType.METHOD_FORMAL_PARAMETER;
        }
    }

    public record ThrowsTargetImpl(int throwsTargetIndex) implements ThrowsTarget {
        @Override
        public TargetType targetType() {
            return TargetType.THROWS;
        }
    }

    public record LocalVarTargetImpl(TargetType targetType, List<LocalVarTargetInfo> table)
            implements LocalVarTarget {

        public LocalVarTargetImpl(TargetType targetType, List<LocalVarTargetInfo> table) {
            this.targetType = checkValid(targetType, TARGET_LOCAL_VARIABLE, TARGET_RESOURCE_VARIABLE);
            this.table = List.copyOf(table);
        }
        @Override
        public int size() {
            return 2 + 6 * table.size();
        }
    }

    public record LocalVarTargetInfoImpl(Label startLabel, Label endLabel, int index)
            implements LocalVarTargetInfo {

        public LocalVarTargetInfoImpl {
            requireNonNull(startLabel);
            requireNonNull(endLabel);
        }
    }

    public record CatchTargetImpl(int exceptionTableIndex) implements CatchTarget {
        @Override
        public TargetType targetType() {
            return TargetType.EXCEPTION_PARAMETER;
        }
    }

    public record OffsetTargetImpl(TargetType targetType, Label target) implements OffsetTarget {

        public OffsetTargetImpl(TargetType targetType, Label target) {
            this.targetType = checkValid(targetType, TARGET_INSTANCEOF, TARGET_METHOD_REFERENCE);
            this.target = requireNonNull(target);
        }
    }

    public record TypeArgumentTargetImpl(TargetType targetType, Label target, int typeArgumentIndex)
            implements TypeArgumentTarget {

        public TypeArgumentTargetImpl(TargetType targetType, Label target, int typeArgumentIndex) {
            this.targetType = checkValid(targetType, TARGET_CAST, TARGET_METHOD_REFERENCE_TYPE_ARGUMENT);
            this.target = requireNonNull(target);
            this.typeArgumentIndex = typeArgumentIndex;
        }
    }
}
