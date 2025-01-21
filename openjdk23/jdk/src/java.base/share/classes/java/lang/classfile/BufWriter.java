/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package java.lang.classfile;

import java.lang.classfile.constantpool.ConstantPool;
import java.lang.classfile.constantpool.ConstantPoolBuilder;
import java.lang.classfile.constantpool.PoolEntry;
import java.nio.ByteOrder;

import jdk.internal.classfile.impl.BufWriterImpl;

/**
 * Advanced {@code class} file writing support for {@link AttributeMapper}s.
 * Supports writing portions of a {@code class} file to a growable buffer, such
 * as writing various numerical types (e.g., {@code u2}, {@code u4}), to the end
 * of the buffer, as well as to create constant pool entries.
 * <p>
 * All numeric values in the {@code class} file format are {@linkplain
 * ByteOrder#BIG_ENDIAN big endian}.  Writing larger numeric values to smaller
 * numeric values are always done with truncation, that the least significant
 * bytes are kept and the other bytes are silently dropped.  As a result,
 * numeric value writing methods can write both signed and unsigned values, and
 * users should validate their values before writing if silent dropping of most
 * significant bytes is not the intended behavior.
 *
 * @see AttributeMapper#writeAttribute(BufWriter, Attribute)
 * @since 24
 */
public sealed interface BufWriter
        permits BufWriterImpl {

    /**
     * {@return the constant pool builder associated with this buffer}
     *
     * @see ClassFileBuilder#constantPool()
     */
    ConstantPoolBuilder constantPool();

    /**
     * {@return whether the provided constant pool is index-compatible with the
     * constant pool of this buffer}
     * <p>
     * This is a shortcut for {@code constantPool().canWriteDirect(other)}.
     *
     * @param other the other constant pool
     * @see ConstantPoolBuilder#canWriteDirect(ConstantPool)
     */
    boolean canWriteDirect(ConstantPool other);

    /**
     * Ensures that the buffer has at least {@code freeBytes} bytes of free space
     * in the end of the buffer.
     * <p>
     * The writing result is the same without calls to this method, but the
     * writing process may be slower.
     *
     * @apiNote
     * This is a hint that changes no visible state of the buffer; it helps to
     * reduce reallocation of the underlying storage by allocating sufficient
     * space at once.
     *
     * @param freeBytes the number of bytes to reserve
     */
    void reserveSpace(int freeBytes);

    /**
     * Writes a byte to the buffer.  {@code x} is truncated to a byte and
     * written.
     *
     * @param x the value to truncate to a byte
     */
    void writeU1(int x);

    /**
     * Writes 2 bytes, or a short, to the buffer.  {@code x} is truncated to two
     * bytes and written.
     *
     * @param x the value to truncate to a short
     */
    void writeU2(int x);

    /**
     * Writes 4 bytes, or an int, to the buffer.
     *
     * @param x the int value
     */
    void writeInt(int x);

    /**
     * Writes a float value, of 4 bytes, to the buffer.
     * <p>
     * In the conversions, all NaN values of the {@code float} may or may not be
     * collapsed into a single {@linkplain Float#NaN "canonical" NaN value}.
     *
     * @param x the float value
     */
    void writeFloat(float x);

    /**
     * Writes 8 bytes, or a long, to the buffer.
     *
     * @param x the long value
     */
    void writeLong(long x);

    /**
     * Writes a double value, of 8 bytes, to the buffer.
     * <p>
     * In the conversions, all NaN values of the {@code double} may or may not
     * be collapsed into a single {@linkplain Double#NaN "canonical" NaN value}.
     *
     * @param x the double value
     */
    void writeDouble(double x);

    /**
     * Writes the contents of a byte array to the buffer.
     *
     * @param arr the byte array
     */
    void writeBytes(byte[] arr);

    /**
     * Writes a range of a byte array to the buffer.
     *
     * @param arr the byte array
     * @param start the start offset of the range within the byte array
     * @param length the length of the range
     * @throws IndexOutOfBoundsException if range is outside the array bounds
     */
    void writeBytes(byte[] arr, int start, int length);

    /**
     * Patches a previously written integer value.  {@code value} is truncated
     * to the given {@code size} number of bytes and written at the given {@code
     * offset}.  The end of this buffer stays unchanged.
     *
     * @apiNote
     * The {@code offset} can be obtained by calling {@link #size()} before
     * writing the previous integer value.
     *
     * @param offset the offset in this buffer at which to patch
     * @param size the size of the integer value being written, in bytes
     * @param value the integer value to be truncated
     * @throws IndexOutOfBoundsException if patched int is outside of bounds
     * @see #size()
     */
    void patchInt(int offset, int size, int value);

    /**
     * Writes a multibyte value to the buffer.  {@code intValue} is truncated
     * to the given {@code intSize} number of bytes and written.
     *
     * @param intSize the size of the integer value being written, in bytes
     * @param intValue the value to be truncated
     */
    void writeIntBytes(int intSize, long intValue);

    /**
     * Writes the index of the specified constant pool entry as a {@link
     * #writeU2 u2}.  If the {@code entry} does not belong to the {@linkplain
     * #constantPool() constant pool} of this buffer, it will be {@linkplain
     * ConstantPoolBuilder##alien converted}, and the index of the converted
     * pool entry is written instead.
     *
     * @param entry the constant pool entry
     * @throws IllegalArgumentException if the entry has invalid index
     */
    void writeIndex(PoolEntry entry);

    /**
     * Writes the index of the specified constant pool entry, or the value
     * {@code 0} if the specified entry is {@code null}, as a {@link #writeU2
     * u2}.  If the {@code entry} does not belong to the {@linkplain
     * #constantPool() constant pool} of this buffer, it will be {@linkplain
     * ConstantPoolBuilder##alien converted}, and the index of the converted
     * pool entry is written instead.
     *
     * @param entry the constant pool entry, may be {@code null}
     * @throws IllegalArgumentException if the entry is not {@code null} and has
     *         invalid index
     */
    void writeIndexOrZero(PoolEntry entry);

    /**
     * {@return the number of bytes that have been written to the buffer}
     *
     * @see #patchInt(int, int, int)
     */
    int size();
}
