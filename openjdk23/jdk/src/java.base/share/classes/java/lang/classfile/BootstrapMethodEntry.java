/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package java.lang.classfile;

import java.lang.classfile.attribute.BootstrapMethodsAttribute;
import java.lang.classfile.constantpool.ConstantPool;
import java.lang.classfile.constantpool.ConstantPoolBuilder;
import java.lang.classfile.constantpool.LoadableConstantEntry;
import java.lang.classfile.constantpool.MethodHandleEntry;
import java.util.List;

import jdk.internal.classfile.impl.BootstrapMethodEntryImpl;

/**
 * Models an entry in the bootstrap method table.  The bootstrap method table
 * is stored in the {@link BootstrapMethodsAttribute BootstrapMethods}
 * attribute, but is modeled by the {@link ConstantPool}, since the bootstrap
 * method table is logically part of the constant pool.
 * <p>
 * A bootstrap method entry is composite:
 * {@snippet lang=text :
 * // @link substring="BootstrapMethodEntry" target="ConstantPoolBuilder#bsmEntry(MethodHandleEntry, List)" :
 * BootstrapMethodEntry(
 *     MethodHandleEntry bootstrapMethod, // @link substring="bootstrapMethod" target="#bootstrapMethod"
 *     List<LoadableConstantEntry> arguments // @link substring="arguments" target="#arguments()"
 * )
 * }
 *
 * @see ConstantPoolBuilder#bsmEntry ConstantPoolBuilder::bsmEntry
 * @since 24
 */
public sealed interface BootstrapMethodEntry
        permits BootstrapMethodEntryImpl {

    /**
     * {@return the constant pool associated with this entry}
     *
     * @apiNote
     * Given a {@link ConstantPoolBuilder} {@code builder} and a {@code
     * BootstrapMethodEntry} {@code entry}, use {@link
     * ConstantPoolBuilder#canWriteDirect
     * builder.canWriteDirect(entry.constantPool())} instead of object equality
     * of the constant pool to determine if an entry is compatible.
     */
    ConstantPool constantPool();

    /**
     * {@return the index into the bootstrap method table corresponding to this
     * entry}
     */
    int bsmIndex();

    /**
     * {@return the bootstrap method}
     */
    MethodHandleEntry bootstrapMethod();

    /**
     * {@return the bootstrap arguments}
     */
    List<LoadableConstantEntry> arguments();
}
