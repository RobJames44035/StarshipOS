/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package compiler.vectorapi.reshape.utils;

import jdk.incubator.vector.VectorSpecies;

public record VectorSpeciesPair(VectorSpecies<?> isp, VectorSpecies<?> osp, boolean unsignedCast) {
    public static VectorSpeciesPair makePair(VectorSpecies<?> isp, VectorSpecies<?> osp, boolean unsignedCast) {
        return new VectorSpeciesPair(isp, osp, unsignedCast);
    }

    public static VectorSpeciesPair makePair(VectorSpecies<?> isp, VectorSpecies<?> osp) {
        return new VectorSpeciesPair(isp, osp, false);
    }

    public String format() {
        return String.format("test%s%c%dto%c%d",
                unsignedCast() ? "U" : "",
                Character.toUpperCase(isp().elementType().getName().charAt(0)),
                isp().vectorBitSize(),
                Character.toUpperCase(osp().elementType().getName().charAt(0)),
                osp().vectorBitSize());
    }
}
