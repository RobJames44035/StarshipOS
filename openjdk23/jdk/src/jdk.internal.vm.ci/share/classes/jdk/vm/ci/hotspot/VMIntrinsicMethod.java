/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */
package jdk.vm.ci.hotspot;

import jdk.vm.ci.meta.Signature;

/**
 * Describes a method for which the VM has an intrinsic implementation.
 */
public final class VMIntrinsicMethod {

    /**
     * The name of the class declaring the intrinsified method. The name is in class file format
     * (see JVMS {@jvms 4.2.1}). For example, {@code "java/lang/Thread"} instead of
     * {@code "java.lang.Thread"}.
     */
    public final String declaringClass;

    /**
     * The name of the intrinsified method. This is not guaranteed to be a legal method name (e.g.,
     * there is a HotSpot intrinsic with the name {@code "<compiledLambdaForm>"}).
     */
    public final String name;

    /**
     * The {@link Signature#toMethodDescriptor() descriptor} of the intrinsified method. This is not
     * guaranteed to be a legal method descriptor (e.g., intrinsics for signature polymorphic
     * methods have a descriptor of {@code "*"}).
     */
    public final String descriptor;

    /**
     * The unique VM identifier for the intrinsic.
     */
    public final int id;

    /**
     * This value reflects the `ControlIntrinsic`, `DisableIntrinsic` and `UseXXXIntrinsic` VM flags
     * as well as other factors such as the current CPU.
     */
    public final boolean isAvailable;

    /**
     * True if this intrinsic is supported by C1.
     */
    public final boolean c1Supported;

    /**
     * True if this intrinsic is supported by C2.
     */
    public final boolean c2Supported;

    @VMEntryPoint
    VMIntrinsicMethod(String declaringClass, String name, String descriptor, int id,
                             boolean isAvailable, boolean c1Supported, boolean c2Supported) {
        this.declaringClass = declaringClass;
        this.name = name;
        this.descriptor = descriptor;
        this.id = id;
        this.isAvailable = isAvailable;
        this.c1Supported = c1Supported;
        this.c2Supported = c2Supported;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof VMIntrinsicMethod) {
            VMIntrinsicMethod that = (VMIntrinsicMethod) obj;
            if (that.id == this.id) {
                assert that.name.equals(this.name) &&
                                that.declaringClass.equals(this.declaringClass) &&
                                that.descriptor.equals(this.descriptor) &&
                                that.isAvailable == this.isAvailable &&
                                that.c1Supported == this.c1Supported &&
                                that.c2Supported == this.c2Supported;
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("IntrinsicMethod[declaringClass=%s, name=%s, descriptor=%s, id=%d, isAvailable=%b, c1Supported=%b, c2Supported=%b]",
                        declaringClass, name, descriptor, id, isAvailable, c1Supported, c2Supported);
    }
}
