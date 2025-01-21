/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package java.lang.classfile.attribute;

import java.lang.classfile.Attribute;
import java.lang.classfile.AttributeMapper;
import java.lang.classfile.AttributeMapper.AttributeStability;
import java.lang.classfile.Attributes;
import java.lang.classfile.ClassElement;
import java.lang.classfile.ClassModel;
import java.lang.classfile.constantpool.Utf8Entry;
import java.util.List;

import jdk.internal.classfile.impl.BoundAttribute;
import jdk.internal.classfile.impl.TemporaryConstantPool;
import jdk.internal.classfile.impl.UnboundAttribute;

/**
 * Models the {@link Attributes#moduleHashes() ModuleHashes} attribute, which
 * appears on classes that {@linkplain ClassModel#isModuleInfo() represent}
 * module descriptors to capture the hashes of a set of co-delivered modules.
 * <p>
 * The specification of the {@code ModuleHashes} attribute is:
 * <pre> {@code
 *
 * ModuleHashes_attribute {
 *   // index to CONSTANT_utf8_info structure in constant pool representing
 *   // the string "ModuleHashes"
 *   u2 attribute_name_index;
 *   u4 attribute_length;
 *
 *   // index to CONSTANT_utf8_info structure with algorithm name
 *   u2 algorithm_index;
 *
 *   // the number of entries in the hashes table
 *   u2 hashes_count;
 *   {   u2 module_name_index (index to CONSTANT_Module_info structure)
 *       u2 hash_length;
 *       u1 hash[hash_length];
 *   } hashes[hashes_count];
 *
 * }
 * } </pre>
 * <p>
 * This attribute only appears on classes, and does not permit {@linkplain
 * AttributeMapper#allowMultiple multiple instances} in a class.  It has a
 * data dependency on the {@linkplain AttributeStability#CP_REFS constant pool}.
 * <p>
 * This attribute is not predefined in the Java SE Platform.  This is a
 * JDK-specific nonstandard attribute produced by the {@code jdk.jlink} module,
 * which defines the {@code jlink} and {@code jmod} tools.
 *
 * @see Attributes#moduleHashes()
 * @see ModuleResolutionAttribute
 * @see ModuleTargetAttribute
 * @since 24
 */
public sealed interface ModuleHashesAttribute
        extends Attribute<ModuleHashesAttribute>, ClassElement
        permits BoundAttribute.BoundModuleHashesAttribute, UnboundAttribute.UnboundModuleHashesAttribute {

    /**
     * {@return the algorithm name used to compute the hash}
     */
    Utf8Entry algorithm();

    /**
     * {@return the hash information about related modules}
     */
    List<ModuleHashInfo> hashes();

    /**
     * {@return a {@code ModuleHashes} attribute}
     * @param algorithm the hashing algorithm
     * @param hashes the hash descriptions
     */
    static ModuleHashesAttribute of(String algorithm,
                                    List<ModuleHashInfo> hashes) {
        return of(TemporaryConstantPool.INSTANCE.utf8Entry(algorithm), hashes);
    }

    /**
     * {@return a {@code ModuleHashes} attribute}
     * @param algorithm the hashing algorithm
     * @param hashes the hash descriptions
     */
    static ModuleHashesAttribute of(String algorithm,
                                    ModuleHashInfo... hashes) {
        return of(algorithm, List.of(hashes));
    }

    /**
     * {@return a {@code ModuleHashes} attribute}
     * @param algorithm the hashing algorithm
     * @param hashes the hash descriptions
     */
    static ModuleHashesAttribute of(Utf8Entry algorithm,
                                    List<ModuleHashInfo> hashes) {
        return new UnboundAttribute.UnboundModuleHashesAttribute(algorithm, hashes);
    }

    /**
     * {@return a {@code ModuleHashes} attribute}
     * @param algorithm the hashing algorithm
     * @param hashes the hash descriptions
     */
    static ModuleHashesAttribute of(Utf8Entry algorithm,
                                    ModuleHashInfo... hashes) {
        return of(algorithm, List.of(hashes));
    }
}
