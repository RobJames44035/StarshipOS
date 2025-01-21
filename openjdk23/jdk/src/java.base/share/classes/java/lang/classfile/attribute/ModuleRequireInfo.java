/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package java.lang.classfile.attribute;

import java.lang.classfile.constantpool.ModuleEntry;
import java.lang.classfile.constantpool.Utf8Entry;
import java.lang.constant.ModuleDesc;
import java.lang.module.ModuleDescriptor;
import java.lang.reflect.AccessFlag;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import jdk.internal.classfile.impl.TemporaryConstantPool;
import jdk.internal.classfile.impl.UnboundAttribute;
import jdk.internal.classfile.impl.Util;

/**
 * Models a single "requires" declaration in the {@link ModuleAttribute}.
 *
 * @see ModuleAttribute#requires()
 * @see ModuleDescriptor#requires()
 * @jvms 4.7.25 The {@code Module} Attribute
 * @since 24
 */
public sealed interface ModuleRequireInfo
        permits UnboundAttribute.UnboundModuleRequiresInfo {

    /**
     * {@return The module on which the current module depends}
     */
    ModuleEntry requires();

    /**
     * {@return the flags associated with this require declaration, as a bit mask}
     *
     * @see ModuleDescriptor.Requires#modifiers()
     * @see AccessFlag.Location#MODULE_REQUIRES
     */
    int requiresFlagsMask();

    /**
     * {@return the flags associated with this require declaration, as a set of
     * flag enums}
     *
     * @throws IllegalArgumentException if the flags mask has any undefined bit set
     * @see ModuleDescriptor.Requires#accessFlags()
     * @see AccessFlag.Location#MODULE_REQUIRES
     */
    default Set<AccessFlag> requiresFlags() {
        return AccessFlag.maskToAccessFlags(requiresFlagsMask(), AccessFlag.Location.MODULE_REQUIRES);
    }

    /**
     * {@return the required version of the required module, if present}
     *
     * @see ModuleDescriptor.Requires#rawCompiledVersion()
     */
    Optional<Utf8Entry> requiresVersion();

    /**
     * {@return whether the specific access flag is set}
     *
     * @param flag the access flag
     * @see AccessFlag.Location#MODULE_REQUIRES
     */
    default boolean has(AccessFlag flag) {
        return Util.has(AccessFlag.Location.MODULE_REQUIRES, requiresFlagsMask(), flag);
    }

    /**
     * {@return a module requirement description}
     *
     * @param requires the required module
     * @param requiresFlags the require-specific flags
     * @param requiresVersion the required version, may be {@code null}
     */
    static ModuleRequireInfo of(ModuleEntry requires, int requiresFlags, Utf8Entry requiresVersion) {
        return new UnboundAttribute.UnboundModuleRequiresInfo(requires, requiresFlags, Optional.ofNullable(requiresVersion));
    }

    /**
     * {@return a module requirement description}
     *
     * @param requires the required module
     * @param requiresFlags the require-specific flags
     * @param requiresVersion the required version, may be {@code null}
     * @throws IllegalArgumentException if any flag cannot be applied to the
     *         {@link AccessFlag.Location#MODULE_REQUIRES} location
     */
    static ModuleRequireInfo of(ModuleEntry requires, Collection<AccessFlag> requiresFlags, Utf8Entry requiresVersion) {
        return of(requires, Util.flagsToBits(AccessFlag.Location.MODULE_REQUIRES, requiresFlags), requiresVersion);
    }

    /**
     * {@return a module requirement description}
     *
     * @param requires the required module
     * @param requiresFlags the require-specific flags
     * @param requiresVersion the required version, may be {@code null}
     */
    static ModuleRequireInfo of(ModuleDesc requires, int requiresFlags, String requiresVersion) {
        return new UnboundAttribute.UnboundModuleRequiresInfo(TemporaryConstantPool.INSTANCE.moduleEntry(TemporaryConstantPool.INSTANCE.utf8Entry(requires.name())), requiresFlags, Optional.ofNullable(requiresVersion).map(s -> TemporaryConstantPool.INSTANCE.utf8Entry(s)));
    }

    /**
     * {@return a module requirement description}
     *
     * @param requires the required module
     * @param requiresFlags the require-specific flags
     * @param requiresVersion the required version, may be {@code null}
     * @throws IllegalArgumentException if any flag cannot be applied to the
     *         {@link AccessFlag.Location#MODULE_REQUIRES} location
     */
    static ModuleRequireInfo of(ModuleDesc requires, Collection<AccessFlag> requiresFlags, String requiresVersion) {
        return of(requires, Util.flagsToBits(AccessFlag.Location.MODULE_REQUIRES, requiresFlags), requiresVersion);
    }
}
