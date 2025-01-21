/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */
package jdk.internal.constant;

import java.lang.constant.ClassDesc;
import java.lang.invoke.MethodHandles;

import jdk.internal.vm.annotation.Stable;

import static jdk.internal.constant.ConstantUtils.*;

/**
 * A class or interface descriptor.
 * Restrictions:
 * <ul>
 * <li>Starts with 'L'
 * <li>Ends with ';'
 * <li>No '.' or '[' or ';' in the middle
 * <li>No leading/trailing/consecutive '/'
 * </ul>
 */
public final class ClassOrInterfaceDescImpl implements ClassDesc {
    private final String descriptor;
    private @Stable String internalName;

    /**
     * Creates a {@linkplain ClassOrInterfaceDescImpl} from a pre-validated descriptor string
     * for a class or interface.
     */
    public static ClassOrInterfaceDescImpl ofValidated(String descriptor) {
        assert ConstantUtils.skipOverFieldSignature(descriptor, 0, descriptor.length())
                == descriptor.length() : descriptor;
        assert descriptor.charAt(0) == 'L';
        return new ClassOrInterfaceDescImpl(descriptor);
    }

    ClassOrInterfaceDescImpl(String descriptor) {
        this.descriptor = descriptor;
    }

    public String internalName() {
        var internalName = this.internalName;
        if (internalName == null) {
            this.internalName = internalName = dropFirstAndLastChar(descriptor);
        }
        return internalName;
    }

    @Override
    public ClassDesc arrayType(int rank) {
        ConstantUtils.validateArrayRank(rank);
        return ArrayClassDescImpl.ofValidated(this, rank);
    }

    @Override
    public ClassDesc arrayType() {
        return ArrayClassDescImpl.ofValidated(this, 1);
    }

    @Override
    public ClassDesc nested(String nestedName) {
        validateMemberName(nestedName, false);
        String desc = descriptorString();
        StringBuilder sb = new StringBuilder(desc.length() + nestedName.length() + 1);
        sb.append(desc, 0, desc.length() - 1).append('$').append(nestedName).append(';');
        return ofValidated(sb.toString());
    }

    @Override
    public ClassDesc nested(String firstNestedName, String... moreNestedNames) {
        validateMemberName(firstNestedName, false);
        // implicit null-check
        for (String addNestedNames : moreNestedNames) {
            validateMemberName(addNestedNames, false);
        }
        return moreNestedNames.length == 0
                ? nested(firstNestedName)
                : nested(firstNestedName + "$" + String.join("$", moreNestedNames));

    }

    @Override
    public boolean isClassOrInterface() {
        return true;
    }

    @Override
    public String packageName() {
        String desc = descriptorString();
        int index = desc.lastIndexOf('/');
        return (index == -1) ? "" : internalToBinary(desc.substring(1, index));
    }

    @Override
    public String displayName() {
        String desc = descriptorString();
        return desc.substring(Math.max(1, desc.lastIndexOf('/') + 1), desc.length() - 1);
    }

    @Override
    public String descriptorString() {
        return descriptor;
    }

    @Override
    public Class<?> resolveConstantDesc(MethodHandles.Lookup lookup)
            throws ReflectiveOperationException {
        return lookup.findClass(internalToBinary(internalName()));
    }

    /**
     * Returns {@code true} if this {@linkplain ClassOrInterfaceDescImpl} is
     * equal to another {@linkplain ClassOrInterfaceDescImpl}.  Equality is
     * determined by the two class descriptors having equal class descriptor
     * strings.
     *
     * @param o the {@code ClassDesc} to compare to this
     *       {@code ClassDesc}
     * @return {@code true} if the specified {@code ClassDesc}
     *      is equal to this {@code ClassDesc}.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof ClassOrInterfaceDescImpl constant) {
            return descriptor.equals(constant.descriptor);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return descriptor.hashCode();
    }

    @Override
    public String toString() {
        return String.format("ClassOrInterfaceDesc[%s]", displayName());
    }
}
