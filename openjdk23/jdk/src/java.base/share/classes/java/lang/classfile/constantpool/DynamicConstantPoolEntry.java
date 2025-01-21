/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package java.lang.classfile.constantpool;

import java.lang.classfile.BootstrapMethodEntry;

/**
 * Superinterface modeling dynamically-computed constant pool entries, which
 * include {@link ConstantDynamicEntry} and {@link InvokeDynamicEntry}, in the
 * constant pool of a {@code class} file.
 * <p>
 * Different types of dynamically-computed constant pool entries bear structural
 * similarities, but they appear in distinct locations.  As a result, their uses
 * are represented by different symbolic descriptors, specific to each subtype.
 * <p>
 * A dynamic constant entry is composite:
 * {@snippet lang=text :
 * DynamicConstantPoolEntry(
 *     BootstrapMethodEntry bootstrap, // @link substring="bootstrap" target="#bootstrap()"
 *     NameAndTypeEntry nameAndType // @link substring="nameAndType" target="#nameAndType()"
 * )
 * }
 *
 * @see java.lang.invoke##jvm_mods Dynamic resolution of call sites and
 *      constants
 * @jvms 4.4.10 The {@code CONSTANT_Dynamic_info} and {@code
 *              CONSTANT_InvokeDynamic_info} Structures
 * @jvms 5.4.3.6 Dynamically-Computed Constant and Call Site Resolution
 * @sealedGraph
 * @since 24
 */
public sealed interface DynamicConstantPoolEntry extends PoolEntry
        permits ConstantDynamicEntry, InvokeDynamicEntry {

    /**
     * {@return the entry in the bootstrap method table for this constant}
     *
     * @see java.lang.invoke##bsm Execution of bootstrap methods
     * @see ConstantPoolBuilder#constantDynamicEntry(BootstrapMethodEntry, NameAndTypeEntry)
     *      ConstantPoolBuilder::constantDynamicEntry(BootstrapMethodEntry, NameAndTypeEntry)
     * @see ConstantPoolBuilder#invokeDynamicEntry(BootstrapMethodEntry, NameAndTypeEntry)
     *      ConstantPoolBuilder::invokeDynamicEntry(BootstrapMethodEntry, NameAndTypeEntry)
     */
    BootstrapMethodEntry bootstrap();

    /**
     * {@return index of the entry in the bootstrap method table for this
     * constant}  The return value is equivalent to {@code
     * bootstrap().bsmIndex()}.
     */
    int bootstrapMethodIndex();

    /**
     * {@return the name and the descriptor string indicated by this symbolic
     * reference}
     *
     * @see java.lang.invoke##bsm Execution of bootstrap methods
     * @see ConstantPoolBuilder#constantDynamicEntry(BootstrapMethodEntry, NameAndTypeEntry)
     *      ConstantPoolBuilder::constantDynamicEntry(BootstrapMethodEntry, NameAndTypeEntry)
     * @see ConstantPoolBuilder#invokeDynamicEntry(BootstrapMethodEntry, NameAndTypeEntry)
     *      ConstantPoolBuilder::invokeDynamicEntry(BootstrapMethodEntry, NameAndTypeEntry)
     */
    NameAndTypeEntry nameAndType();

    /**
     * {@return the name indicated by this symbolic reference}
     */
    default Utf8Entry name() {
        return nameAndType().name();
    }

    /**
     * {@return the descriptor string indicated by this symbolic reference}
     * This is a field descriptor string if this entry is a {@link
     * ConstantDynamicEntry}, or a method descriptor string if this entry is a
     * {@link InvokeDynamicEntry}.
     *
     * @apiNote
     * Each subinterface has its specific accessor named {@code typeSymbol} for
     * the symbolic descriptor derived from this descriptor string.
     */
    default Utf8Entry type() {
        return nameAndType().type();
    }
}
