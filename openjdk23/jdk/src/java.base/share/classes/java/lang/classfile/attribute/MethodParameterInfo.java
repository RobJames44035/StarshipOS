/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package java.lang.classfile.attribute;

import java.lang.classfile.constantpool.Utf8Entry;
import java.lang.reflect.AccessFlag;
import java.lang.reflect.Parameter;
import java.util.Optional;
import java.util.Set;

import jdk.internal.classfile.impl.TemporaryConstantPool;
import jdk.internal.classfile.impl.UnboundAttribute;
import jdk.internal.classfile.impl.Util;

/**
 * Models a single method parameter in the {@link MethodParametersAttribute}.
 *
 * @see MethodParametersAttribute#parameters()
 * @see Parameter
 * @jvms 4.7.24 The {@code MethodParameters} Attribute
 * @since 24
 */
public sealed interface MethodParameterInfo
        permits UnboundAttribute.UnboundMethodParameterInfo {
    /**
     * {@return the parameter name, if it has one}
     *
     * @see Parameter#getName()
     * @see Parameter#isNamePresent()
     */
    Optional<Utf8Entry> name();

    /**
     * {@return the access flags, as a bit mask}
     *
     * @see Parameter#getModifiers()
     * @see AccessFlag.Location#METHOD_PARAMETER
     */
    int flagsMask();

    /**
     * {@return the access flags, as a set of flag enums}
     *
     * @throws IllegalArgumentException if the flags mask has any undefined bit set
     * @see Parameter#accessFlags()
     * @see AccessFlag.Location#METHOD_PARAMETER
     */
    default Set<AccessFlag> flags() {
        return AccessFlag.maskToAccessFlags(flagsMask(), AccessFlag.Location.METHOD_PARAMETER);
    }

    /**
     * {@return whether the method parameter has a specific flag set}
     * @param flag the method parameter flag
     * @see AccessFlag.Location#METHOD_PARAMETER
     */
    default boolean has(AccessFlag flag) {
        return Util.has(AccessFlag.Location.METHOD_PARAMETER, flagsMask(), flag);
    }

    /**
     * {@return a method parameter description}
     * @param name the method parameter name, may be empty
     * @param flags the method parameter access flags
     */
    static MethodParameterInfo of(Optional<Utf8Entry> name, int flags) {
        return new UnboundAttribute.UnboundMethodParameterInfo(name, flags);
    }

    /**
     * {@return a method parameter description}
     * @param name the method parameter name, may be empty
     * @param flags the method parameter access flags
     * @throws IllegalArgumentException if any flag cannot be applied to the
     *         {@link AccessFlag.Location#METHOD_PARAMETER} location
     */
    static MethodParameterInfo of(Optional<String> name, AccessFlag... flags) {
        return of(name.map(TemporaryConstantPool.INSTANCE::utf8Entry), Util.flagsToBits(AccessFlag.Location.METHOD_PARAMETER, flags));
    }

    /**
     * {@return a method parameter description}
     * @param name the method parameter name, may be empty
     * @param flags the method parameter access flags
     */
    static MethodParameterInfo ofParameter(Optional<String> name, int flags) {
        return of(name.map(TemporaryConstantPool.INSTANCE::utf8Entry), flags);
    }
}
