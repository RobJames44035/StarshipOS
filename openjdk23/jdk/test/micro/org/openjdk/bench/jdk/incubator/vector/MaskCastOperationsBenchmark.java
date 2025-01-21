/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package org.openjdk.bench.jdk.incubator.vector;

import jdk.incubator.vector.*;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.*;

@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Fork(jvmArgs = {"--add-modules=jdk.incubator.vector"})
public class MaskCastOperationsBenchmark {
    VectorMask<Byte> bmask64;
    VectorMask<Byte> bmask128;
    VectorMask<Byte> bmask256;

    VectorMask<Short> smask64;
    VectorMask<Short> smask128;
    VectorMask<Short> smask256;
    VectorMask<Short> smask512;

    VectorMask<Integer> imask64;
    VectorMask<Integer> imask128;
    VectorMask<Integer> imask256;
    VectorMask<Integer> imask512;

    VectorMask<Long> lmask128;
    VectorMask<Long> lmask256;
    VectorMask<Long> lmask512;

    static final boolean [] mask_arr = {
       false, false, false, true, false, false, false, false,
       false, false, false, true, false, false, false, false,
       false, false, false, true, false, false, false, false,
       true, true, true, true, true, true, true, true,
       true, true, true, true, true, true, true, true,
       false, false, false, true, false, false, false, false,
       false, false, false, true, false, false, false, false,
       false, false, false, true, false, false, false, false
    };

    @Setup(Level.Trial)
    public void BmSetup() {
        bmask64 = VectorMask.fromArray(ByteVector.SPECIES_64, mask_arr, 0);
        bmask128 = VectorMask.fromArray(ByteVector.SPECIES_128, mask_arr, 0);
        bmask256 = VectorMask.fromArray(ByteVector.SPECIES_256, mask_arr, 0);

        smask64 = VectorMask.fromArray(ShortVector.SPECIES_64, mask_arr, 0);
        smask128 = VectorMask.fromArray(ShortVector.SPECIES_128, mask_arr, 0);
        smask256 = VectorMask.fromArray(ShortVector.SPECIES_256, mask_arr, 0);
        smask512 = VectorMask.fromArray(ShortVector.SPECIES_512, mask_arr, 0);

        imask64 = VectorMask.fromArray(IntVector.SPECIES_64, mask_arr, 0);
        imask128 = VectorMask.fromArray(IntVector.SPECIES_128, mask_arr, 0);
        imask256 = VectorMask.fromArray(IntVector.SPECIES_256, mask_arr, 0);
        imask512 = VectorMask.fromArray(IntVector.SPECIES_512, mask_arr, 0);

        lmask128 = VectorMask.fromArray(LongVector.SPECIES_128, mask_arr, 0);
        lmask256 = VectorMask.fromArray(LongVector.SPECIES_256, mask_arr, 0);
        lmask512 = VectorMask.fromArray(LongVector.SPECIES_512, mask_arr, 0);
    }

    @Benchmark
    public VectorMask<Short> microMaskCastByte64ToShort128() {
        return bmask64.cast(ShortVector.SPECIES_128);
    }

    @Benchmark
    public VectorMask<Integer> microMaskCastByte64ToInteger256() {
        return bmask64.cast(IntVector.SPECIES_256);
    }

    @Benchmark
    public VectorMask<Long> microMaskCastByte64ToLong512() {
        return bmask64.cast(LongVector.SPECIES_512);
    }

    @Benchmark
    public VectorMask<Short> microMaskCastByte128ToShort256() {
        return bmask128.cast(ShortVector.SPECIES_256);
    }

    @Benchmark
    public VectorMask<Integer> microMaskCastByte128ToInteger512() {
        return bmask128.cast(IntVector.SPECIES_512);
    }

    @Benchmark
    public VectorMask<Short> microMaskCastByte256ToShort512() {
        return bmask256.cast(ShortVector.SPECIES_512);
    }

    @Benchmark
    public VectorMask<Integer> microMaskCastShort64ToInteger128() {
        return smask64.cast(IntVector.SPECIES_128);
    }

    @Benchmark
    public VectorMask<Long> microMaskCastShort64ToLong256() {
        return smask64.cast(LongVector.SPECIES_256);
    }

    @Benchmark
    public VectorMask<Byte> microMaskCastShort128ToByte64() {
        return smask128.cast(ByteVector.SPECIES_64);
    }

    @Benchmark
    public VectorMask<Integer> microMaskCastShort128ToInteger256() {
        return smask128.cast(IntVector.SPECIES_256);
    }

    @Benchmark
    public VectorMask<Long> microMaskCastShort128ToLong512() {
        return smask128.cast(LongVector.SPECIES_512);
    }

    @Benchmark
    public VectorMask<Byte> microMaskCastShort256ToByte128() {
        return smask256.cast(ByteVector.SPECIES_128);
    }

    @Benchmark
    public VectorMask<Integer> microMaskCastShort256ToInteger512() {
        return smask256.cast(IntVector.SPECIES_512);
    }

    @Benchmark
    public VectorMask<Byte> microMaskCastShort512ToByte256() {
        return smask512.cast(ByteVector.SPECIES_256);
    }

    @Benchmark
    public VectorMask<Long> microMaskCastInteger64ToLong128() {
        return imask64.cast(LongVector.SPECIES_128);
    }

    @Benchmark
    public VectorMask<Short> microMaskCastInteger128ToShort64() {
        return imask128.cast(ShortVector.SPECIES_64);
    }

    @Benchmark
    public VectorMask<Long> microMaskCastInteger128ToLong256() {
        return imask128.cast(LongVector.SPECIES_256);
    }

    @Benchmark
    public VectorMask<Byte> microMaskCastInteger256ToByte64() {
        return imask256.cast(ByteVector.SPECIES_64);
    }

    @Benchmark
    public VectorMask<Short> microMaskCastInteger256ToShort128() {
        return imask256.cast(ShortVector.SPECIES_128);
    }

    @Benchmark
    public VectorMask<Long> microMaskCastInteger256ToLong512() {
        return imask256.cast(LongVector.SPECIES_512);
    }

    @Benchmark
    public VectorMask<Byte> microMaskCastInteger512ToByte128() {
        return imask512.cast(ByteVector.SPECIES_128);
    }

    @Benchmark
    public VectorMask<Short> microMaskCastInteger512ToShort256() {
        return imask512.cast(ShortVector.SPECIES_256);
    }

    @Benchmark
    public VectorMask<Integer> microMaskCastLong128ToInteger64() {
        return lmask128.cast(IntVector.SPECIES_64);
    }

    @Benchmark
    public VectorMask<Short> microMaskCastLong256ToShort64() {
        return lmask256.cast(ShortVector.SPECIES_64);
    }

    @Benchmark
    public VectorMask<Integer> microMaskCastLong256ToInteger128() {
        return lmask256.cast(IntVector.SPECIES_128);
    }

    @Benchmark
    public VectorMask<Byte> microMaskCastLong512ToByte64() {
        return lmask512.cast(ByteVector.SPECIES_64);
    }

    @Benchmark
    public VectorMask<Short> microMaskCastLong512ToShort128() {
        return lmask512.cast(ShortVector.SPECIES_128);
    }

    @Benchmark
    public VectorMask<Integer> microMaskCastLong512ToInteger256() {
        return lmask512.cast(IntVector.SPECIES_256);
    }
}
