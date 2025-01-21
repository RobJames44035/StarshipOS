/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package java.lang.classfile.attribute;

import java.lang.classfile.constantpool.ModuleEntry;
import java.lang.classfile.constantpool.PackageEntry;
import java.lang.constant.ModuleDesc;
import java.lang.constant.PackageDesc;
import java.lang.invoke.MethodHandles;
import java.lang.module.ModuleDescriptor;
import java.lang.reflect.AccessFlag;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import jdk.internal.classfile.impl.TemporaryConstantPool;
import jdk.internal.classfile.impl.UnboundAttribute;
import jdk.internal.classfile.impl.Util;

/**
 * Models a single "opens" declaration in the {@link ModuleAttribute}.
 *
 * @apiNote
 * Opening a package to another module allows that other module to gain
 * the same full privilege access as members in this module.  See {@link
 * MethodHandles#privateLookupIn} for more details.
 *
 * @see ModuleAttribute#exports()
 * @see ModuleDescriptor.Opens
 * @jvms 4.7.25 The {@code Module} Attribute
 * @since 24
 */
public sealed interface ModuleOpenInfo
        permits UnboundAttribute.UnboundModuleOpenInfo {

    /**
     * {@return the package being opened}
     *
     * @see ModuleDescriptor.Opens#source()
     */
    PackageEntry openedPackage();

    /**
     * {@return the flags associated with this open declaration, as a bit mask}
     *
     * @see ModuleDescriptor.Opens#modifiers()
     * @see AccessFlag.Location#MODULE_OPENS
     */
    int opensFlagsMask();

    /**
     * {@return the flags associated with this open declaration, as a set of
     * flag enums}
     *
     * @throws IllegalArgumentException if the flags mask has any undefined bit set
     * @see ModuleDescriptor.Opens#accessFlags()
     * @see AccessFlag.Location#MODULE_OPENS
     */
    default Set<AccessFlag> opensFlags() {
        return AccessFlag.maskToAccessFlags(opensFlagsMask(), AccessFlag.Location.MODULE_OPENS);
    }

    /**
     * {@return whether the open declaration has the specified access flag set}
     *
     * @param flag the access flag
     * @see AccessFlag.Location#MODULE_OPENS
     */
    default boolean has(AccessFlag flag) {
        return Util.has(AccessFlag.Location.MODULE_OPENS, opensFlagsMask(), flag);
    }

    /**
     * {@return the modules to which this package is opened, or empty if this
     * is an unqualified open}
     *
     * @see ModuleDescriptor.Opens#isQualified()
     * @see ModuleDescriptor.Opens#targets()
     */
    List<ModuleEntry> opensTo();

    /**
     * {@return a module open description}
     *
     * @param opens the package to open
     * @param opensFlags the open flags
     * @param opensTo the modules to which this package is opened, or empty if
     *               this is an unqualified open
     */
    static ModuleOpenInfo of(PackageEntry opens, int opensFlags,
                             List<ModuleEntry> opensTo) {
        return new UnboundAttribute.UnboundModuleOpenInfo(opens, opensFlags, opensTo);
    }

    /**
     * {@return a module open description}
     *
     * @param opens the package to open
     * @param opensFlags the open flags
     * @param opensTo the modules to which this package is opened, or empty if
     *               this is an unqualified open
     * @throws IllegalArgumentException if any flag cannot be applied to the
     *         {@link AccessFlag.Location#MODULE_OPENS} location
     */
    static ModuleOpenInfo of(PackageEntry opens, Collection<AccessFlag> opensFlags,
                             List<ModuleEntry> opensTo) {
        return of(opens, Util.flagsToBits(AccessFlag.Location.MODULE_OPENS, opensFlags), opensTo);
    }

    /**
     * {@return a module open description}
     *
     * @param opens the package to open
     * @param opensFlags the open flags
     * @param opensTo the modules to which this package is opened, or empty if
     *               this is an unqualified open
     */
    static ModuleOpenInfo of(PackageEntry opens,
                             int opensFlags,
                             ModuleEntry... opensTo) {
        return of(opens, opensFlags, List.of(opensTo));
    }

    /**
     * {@return a module open description}
     *
     * @param opens the package to open
     * @param opensFlags the open flags
     * @param opensTo the modules to which this package is opened, or empty if
     *               this is an unqualified open
     * @throws IllegalArgumentException if any flag cannot be applied to the
     *         {@link AccessFlag.Location#MODULE_OPENS} location
     */
    static ModuleOpenInfo of(PackageEntry opens,
                             Collection<AccessFlag> opensFlags,
                             ModuleEntry... opensTo) {
        return of(opens, Util.flagsToBits(AccessFlag.Location.MODULE_OPENS, opensFlags), opensTo);
    }

    /**
     * {@return a module open description}
     * @param opens the package to open
     * @param opensFlags the open flags
     * @param opensTo the modules to which this package is opened, if it is a
     *                qualified open, or empty
     */
    static ModuleOpenInfo of(PackageDesc opens, int opensFlags,
                             List<ModuleDesc> opensTo) {
        return of(TemporaryConstantPool.INSTANCE.packageEntry(TemporaryConstantPool.INSTANCE.utf8Entry(opens.internalName())),
                opensFlags,
                Util.moduleEntryList(opensTo));
    }

    /**
     * {@return a module open description}
     * @param opens the package to open
     * @param opensFlags the open flags
     * @param opensTo the modules to which this package is opened, if it is a
     *               qualified open, or empty
     * @throws IllegalArgumentException if any flag cannot be applied to the
     *         {@link AccessFlag.Location#MODULE_OPENS} location
     */
    static ModuleOpenInfo of(PackageDesc opens, Collection<AccessFlag> opensFlags,
                             List<ModuleDesc> opensTo) {
        return of(opens, Util.flagsToBits(AccessFlag.Location.MODULE_OPENS, opensFlags), opensTo);
    }

    /**
     * {@return a module open description}
     * @param opens the package to open
     * @param opensFlags the open flags
     * @param opensTo the packages to which this package is opened, or empty if
     *               this is an unqualified open
     */
    static ModuleOpenInfo of(PackageDesc opens,
                             int opensFlags,
                             ModuleDesc... opensTo) {
        return of(opens, opensFlags, List.of(opensTo));
    }

    /**
     * {@return a module open description}
     * @param opens the package to open
     * @param opensFlags the open flags
     * @param opensTo the packages to which this package is opened, or empty if
     *               this is an unqualified open
     * @throws IllegalArgumentException if any flag cannot be applied to the
     *         {@link AccessFlag.Location#MODULE_OPENS} location
     */
    static ModuleOpenInfo of(PackageDesc opens,
                             Collection<AccessFlag> opensFlags,
                             ModuleDesc... opensTo) {
        return of(opens, Util.flagsToBits(AccessFlag.Location.MODULE_OPENS, opensFlags), opensTo);
    }
}
