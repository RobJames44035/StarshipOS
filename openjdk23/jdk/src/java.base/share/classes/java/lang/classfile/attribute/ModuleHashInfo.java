/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package java.lang.classfile.attribute;

import java.lang.classfile.constantpool.ModuleEntry;
import java.lang.constant.ModuleDesc;

import jdk.internal.classfile.impl.TemporaryConstantPool;
import jdk.internal.classfile.impl.UnboundAttribute;

/**
 * Models hash information for a single module in the {@link ModuleHashesAttribute}.
 *
 * @see ModuleHashesAttribute#hashes()
 * @since 24
 */
public sealed interface ModuleHashInfo
        permits UnboundAttribute.UnboundModuleHashInfo {

    /**
     * {@return the name of the related module}
     */
    ModuleEntry moduleName();

    /**
     * {@return the hash of the related module}
     */
    byte[] hash();

    /**
     * {@return a module hash description}
     * @param moduleName the module name
     * @param hash the hash value
     */
    static ModuleHashInfo of(ModuleEntry moduleName, byte[] hash) {
        return new UnboundAttribute.UnboundModuleHashInfo(moduleName, hash);
    }

    /**
     * {@return a module hash description}
     * @param moduleDesc the module name
     * @param hash the hash value
     */
    static ModuleHashInfo of(ModuleDesc moduleDesc, byte[] hash) {
        return new UnboundAttribute.UnboundModuleHashInfo(TemporaryConstantPool.INSTANCE.moduleEntry(TemporaryConstantPool.INSTANCE.utf8Entry(moduleDesc.name())), hash);
    }
}
