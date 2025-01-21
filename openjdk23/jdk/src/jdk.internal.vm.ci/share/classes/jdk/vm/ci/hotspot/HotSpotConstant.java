/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package jdk.vm.ci.hotspot;

import jdk.vm.ci.meta.Constant;

/**
 * A value in a space managed by Hotspot (e.g. heap or metaspace).
 * Some of these values can be referenced with a compressed pointer
 * instead of a full word-sized pointer.
 */
public interface HotSpotConstant extends Constant {

    /**
     * Determines if this constant is compressed.
     */
    boolean isCompressed();

    /**
     * Determines if this constant is compressible.
     */
    boolean isCompressible();

    /**
     * Gets a compressed version of this uncompressed constant.
     *
     * @throws IllegalArgumentException if this constant is not compressible
     */
    Constant compress();

    /**
     * Gets an uncompressed version of this compressed constant.
     *
     * @throws IllegalArgumentException if this is an uncompressed constant
     *         or this constant is not compressible
     */
    Constant uncompress();
}
