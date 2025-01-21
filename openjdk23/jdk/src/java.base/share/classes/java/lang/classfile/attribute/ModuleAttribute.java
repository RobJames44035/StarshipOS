/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package java.lang.classfile.attribute;

import java.lang.classfile.Attribute;
import java.lang.classfile.AttributeMapper;
import java.lang.classfile.Attributes;
import java.lang.classfile.ClassElement;
import java.lang.classfile.ClassFile;
import java.lang.classfile.ClassModel;
import java.lang.classfile.constantpool.ClassEntry;
import java.lang.classfile.constantpool.ModuleEntry;
import java.lang.classfile.constantpool.Utf8Entry;
import java.lang.constant.ClassDesc;
import java.lang.constant.ModuleDesc;
import java.lang.constant.PackageDesc;
import java.lang.invoke.MethodHandles;
import java.lang.module.ModuleDescriptor;
import java.lang.reflect.AccessFlag;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.function.Consumer;

import jdk.internal.classfile.impl.BoundAttribute;
import jdk.internal.classfile.impl.ModuleAttributeBuilderImpl;
import jdk.internal.classfile.impl.UnboundAttribute;
import jdk.internal.classfile.impl.Util;

/**
 * Models the {@link Attributes#module() Module} attribute (JVMS {@jvms 4.7.25}),
 * which always appears on classes that {@linkplain ClassModel#isModuleInfo()
 * represent} module descriptors.
 * <p>
 * This attribute only appears on classes, and does not permit {@linkplain
 * AttributeMapper#allowMultiple multiple instances} in a class.  It has a
 * data dependency on the {@linkplain AttributeMapper.AttributeStability#CP_REFS
 * constant pool}.
 * <p>
 * The attribute was introduced in the Java SE Platform version 9, major version
 * {@value ClassFile#JAVA_9_VERSION}.
 *
 * @see Attributes#module()
 * @see ModuleDescriptor
 * @jvms 4.7.25 The {@code Module} Attribute
 * @since 24
 */
public sealed interface ModuleAttribute
        extends Attribute<ModuleAttribute>, ClassElement
        permits BoundAttribute.BoundModuleAttribute, UnboundAttribute.UnboundModuleAttribute {

    /**
     * {@return the name of the module}
     *
     * @see ModuleDescriptor#name()
     */
    ModuleEntry moduleName();

    /**
     * {@return the module flags of the module, as a bit mask}
     *
     * @see ModuleDescriptor#modifiers()
     * @see AccessFlag.Location#MODULE
     */
    int moduleFlagsMask();

    /**
     * {@return the module flags of the module, as a set of enum constants}
     *
     * @throws IllegalArgumentException if the flags mask has any undefined bit set
     * @see ModuleDescriptor#accessFlags()
     * @see AccessFlag.Location#MODULE
     */
    default Set<AccessFlag> moduleFlags() {
        return AccessFlag.maskToAccessFlags(moduleFlagsMask(), AccessFlag.Location.MODULE);
    }

    /**
     * Tests presence of module flag.
     *
     * @param flag the module flag
     * @return true if the flag is set
     * @see AccessFlag.Location#MODULE
     */
    default boolean has(AccessFlag flag) {
        return Util.has(AccessFlag.Location.MODULE, moduleFlagsMask(), flag);
    }

    /**
     * {@return the version of the module, if present}
     *
     * @see ModuleDescriptor#version()
     */
    Optional<Utf8Entry> moduleVersion();

    /**
     * {@return the modules required by this module}
     *
     * @see ModuleDescriptor#requires()
     */
    List<ModuleRequireInfo> requires();

    /**
     * {@return the packages exported by this module}
     *
     * @see ModuleDescriptor#packages()
     */
    List<ModuleExportInfo> exports();

    /**
     * {@return the packages opened by this module}
     *
     * @apiNote
     * Opening a package to another module allows that other module to gain
     * the same full privilege access as members in this module.  See {@link
     * MethodHandles#privateLookupIn} for more details.
     *
     * @see ModuleDescriptor#opens()
     */
    List<ModuleOpenInfo> opens();

    /**
     * {@return the services used by this module}  Services may be discovered via
     * {@link ServiceLoader}.
     *
     * @see ModuleDescriptor#uses()
     */
    List<ClassEntry> uses();

    /**
     * {@return the service implementations provided by this module}
     *
     * @see ModuleDescriptor#provides()
     */
    List<ModuleProvideInfo> provides();

    /**
     * {@return a {@code Module} attribute}
     *
     * @param moduleName the module name
     * @param moduleFlags the module flags
     * @param moduleVersion the module version, may be {@code null}
     * @param requires the required packages
     * @param exports the exported packages
     * @param opens the opened packages
     * @param uses the consumed services
     * @param provides the provided services
     */
    static ModuleAttribute of(ModuleEntry moduleName, int moduleFlags,
                              Utf8Entry moduleVersion,
                              Collection<ModuleRequireInfo> requires,
                              Collection<ModuleExportInfo> exports,
                              Collection<ModuleOpenInfo> opens,
                              Collection<ClassEntry> uses,
                              Collection<ModuleProvideInfo> provides) {
        return new UnboundAttribute.UnboundModuleAttribute(moduleName, moduleFlags, moduleVersion, requires, exports, opens, uses, provides);
    }

    /**
     * {@return a {@code Module} attribute}
     *
     * @param moduleName the module name
     * @param attrHandler a handler that receives a {@link ModuleAttributeBuilder}
     */
    static ModuleAttribute of(ModuleDesc moduleName,
                              Consumer<ModuleAttributeBuilder> attrHandler) {
        var mb = new ModuleAttributeBuilderImpl(moduleName);
        attrHandler.accept(mb);
        return mb.build();
    }

    /**
     * {@return a {@code Module} attribute}
     *
     * @param moduleName the module name
     * @param attrHandler a handler that receives a {@link ModuleAttributeBuilder}
     */
    static ModuleAttribute of(ModuleEntry moduleName,
                              Consumer<ModuleAttributeBuilder> attrHandler) {
        var mb = new ModuleAttributeBuilderImpl(moduleName);
        attrHandler.accept(mb);
        return mb.build();
    }

    /**
     * A builder for {@link ModuleAttribute Module} attributes.
     *
     * @see ModuleDescriptor.Builder
     * @jvms 4.7.25 The {@code Module} Attribute
     * @since 24
     */
    public sealed interface ModuleAttributeBuilder
            permits ModuleAttributeBuilderImpl {

        /**
         * Sets the module name.
         *
         * @param moduleName the module name
         * @return this builder
         */
        ModuleAttributeBuilder moduleName(ModuleDesc moduleName);

        /**
         * Sets the module flags.
         *
         * @param flagsMask the module flags
         * @return this builder
         */
        ModuleAttributeBuilder moduleFlags(int flagsMask);

        /**
         * Sets the module flags.
         *
         * @param moduleFlags the module flags
         * @return this builder
         * @throws IllegalArgumentException if any flag cannot be applied to the
         *         {@link AccessFlag.Location#MODULE} location
         */
        default ModuleAttributeBuilder moduleFlags(AccessFlag... moduleFlags) {
            return moduleFlags(Util.flagsToBits(AccessFlag.Location.MODULE, moduleFlags));
        }

        /**
         * Sets the module version, which may be {@code null}.
         *
         * @param version the module version, may be {@code null}
         * @return this builder
         */
        ModuleAttributeBuilder moduleVersion(String version);

        /**
         * Adds a module requirement.
         *
         * @param module the required module
         * @param requiresFlagsMask the requires flags
         * @param version the required module version, may be {@code null}
         * @return this builder
         */
        ModuleAttributeBuilder requires(ModuleDesc module, int requiresFlagsMask, String version);

        /**
         * Adds a module requirement.
         *
         * @param module the required module
         * @param requiresFlags the requires flags
         * @param version the required module version, may be {@code null}
         * @return this builder
         * @throws IllegalArgumentException if any flag cannot be applied to the
         *         {@link AccessFlag.Location#MODULE_REQUIRES} location
         */
        default ModuleAttributeBuilder requires(ModuleDesc module, Collection<AccessFlag> requiresFlags, String version) {
            return requires(module, Util.flagsToBits(AccessFlag.Location.MODULE_REQUIRES, requiresFlags), version);
        }

        /**
         * Adds module requirement.
         *
         * @param requires the module require info
         * @return this builder
         */
        ModuleAttributeBuilder requires(ModuleRequireInfo requires);

        /**
         * Adds an exported package.
         *
         * @param pkge the exported package
         * @param exportsFlagsMask the export flags
         * @param exportsToModules the modules to export to, or empty for an unqualified export
         * @return this builder
         */
        ModuleAttributeBuilder exports(PackageDesc pkge, int exportsFlagsMask, ModuleDesc... exportsToModules);

        /**
         * Adds an exported package.
         *
         * @param pkge the exported package
         * @param exportsFlags the export flags
         * @param exportsToModules the modules to export to, or empty for an unqualified export
         * @return this builder
         * @throws IllegalArgumentException if any flag cannot be applied to the
         *         {@link AccessFlag.Location#MODULE_EXPORTS} location
         */
        default ModuleAttributeBuilder exports(PackageDesc pkge, Collection<AccessFlag> exportsFlags, ModuleDesc... exportsToModules) {
            return exports(pkge, Util.flagsToBits(AccessFlag.Location.MODULE_EXPORTS, exportsFlags), exportsToModules);
        }

        /**
         * Adds an exported package.
         *
         * @param exports the module export info
         * @return this builder
         */
        ModuleAttributeBuilder exports(ModuleExportInfo exports);

        /**
         * Opens a package.
         *
         * @apiNote
         * Opening a package to another module allows that other module to gain
         * the same full privilege access as members in this module.  See {@link
         * MethodHandles#privateLookupIn} for more details.
         *
         * @param pkge the opened package
         * @param opensFlagsMask the open package flags
         * @param opensToModules the modules to open to, or empty for an unqualified open
         * @return this builder
         */
        ModuleAttributeBuilder opens(PackageDesc pkge, int opensFlagsMask, ModuleDesc... opensToModules);

        /**
         * Opens a package.
         *
         * @apiNote
         * Opening a package to another module allows that other module to gain
         * the same full privilege access as members in this module.  See {@link
         * MethodHandles#privateLookupIn} for more details.
         *
         * @param pkge the opened package
         * @param opensFlags the open package flags
         * @param opensToModules the modules to open to, or empty for an unqualified open
         * @return this builder
         * @throws IllegalArgumentException if any flag cannot be applied to the
         *         {@link AccessFlag.Location#MODULE_OPENS} location
         */
        default ModuleAttributeBuilder opens(PackageDesc pkge, Collection<AccessFlag> opensFlags, ModuleDesc... opensToModules) {
            return opens(pkge, Util.flagsToBits(AccessFlag.Location.MODULE_OPENS, opensFlags), opensToModules);
        }

        /**
         * Opens a package.
         *
         * @apiNote
         * Opening a package to another module allows that other module to gain
         * the same full privilege access as members in this module.  See {@link
         * MethodHandles#privateLookupIn} for more details.
         *
         * @param opens the module open info
         * @return this builder
         */
        ModuleAttributeBuilder opens(ModuleOpenInfo opens);

        /**
         * Declares use of a service.
         *
         * @param service the service class used
         * @return this builder
         * @throws IllegalArgumentException if {@code service} represents a primitive type
         */
        ModuleAttributeBuilder uses(ClassDesc service);

        /**
         * Declares use of a service.
         *
         * @param uses the service class used
         * @return this builder
         */
        ModuleAttributeBuilder uses(ClassEntry uses);

        /**
         * Declares provision of a service.
         *
         * @param service the service class provided
         * @param implClasses the implementation classes
         * @return this builder
         * @throws IllegalArgumentException if {@code service} or any of the {@code implClasses} represents a primitive type
         */
        ModuleAttributeBuilder provides(ClassDesc service, ClassDesc... implClasses);

        /**
         * Declares provision of a service.
         *
         * @param provides the module provides info
         * @return this builder
         */
        ModuleAttributeBuilder provides(ModuleProvideInfo provides);
    }
}
