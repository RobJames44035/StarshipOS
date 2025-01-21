/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

import jdk.incubator.vector.VectorShape;
import jdk.incubator.vector.VectorMask;
import jdk.incubator.vector.VectorShuffle;
import jdk.incubator.vector.VectorSpecies;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.util.function.IntFunction;
import java.util.List;

/**
 * @test
 * @modules jdk.incubator.vector
 * @modules java.base/jdk.internal.vm.annotation
 * @run testng/othervm  -XX:-TieredCompilation --add-opens jdk.incubator.vector/jdk.incubator.vector=ALL-UNNAMED
 * Vector256ConversionTests
 */

@Test
public class Vector256ConversionTests extends AbstractVectorConversionTest {

    static final VectorShape SHAPE = VectorShape.S_256_BIT;
    static final int BUFFER_SIZE = Integer.getInteger("jdk.incubator.vector.test.buffer-size", 1024);

    @DataProvider
    public Object[][] fixedShapeXfixedShape() {
        return fixedShapeXFixedShapeSpeciesArgs(SHAPE);
    }

    @DataProvider
    public Object[][] fixedShapeXShape() {
        return fixedShapeXShapeSpeciesArgs(SHAPE);
    }

    @DataProvider
    public Object[][] fixedShapeXSegmentedLegalCastSpecies() {
        return fixedShapeXSegmentedCastSpeciesArgs(SHAPE, true);
    }

    @DataProvider
    public Object[][] fixedShapeXSegmentedIllegalCastSpecies() {
        return fixedShapeXSegmentedCastSpeciesArgs(SHAPE, false);
    }

    @Test(dataProvider = "fixedShapeXfixedShape")
    static <I, O> void convert(VectorSpecies<I> src, VectorSpecies<O> dst, IntFunction<?> fa) {
        Object a = fa.apply(BUFFER_SIZE);
        conversion_kernel(src, dst, a, ConvAPI.CONVERT);
    }

    @Test(dataProvider = "fixedShapeXShape")
    static <I, O> void convertShape(VectorSpecies<I> src, VectorSpecies<O> dst, IntFunction<?> fa) {
        Object a = fa.apply(BUFFER_SIZE);
        conversion_kernel(src, dst, a, ConvAPI.CONVERTSHAPE);
    }

    @Test(dataProvider = "fixedShapeXShape")
    static <I, O> void castShape(VectorSpecies<I> src, VectorSpecies<O> dst, IntFunction<?> fa) {
        Object a = fa.apply(BUFFER_SIZE);
        conversion_kernel(src, dst, a, ConvAPI.CASTSHAPE);
    }

    @Test(dataProvider = "fixedShapeXShape")
    static <I, O> void reinterpret(VectorSpecies<I> src, VectorSpecies<O> dst, IntFunction<?> fa) {
        Object a = fa.apply(BUFFER_SIZE);
        reinterpret_kernel(src, dst, a);
    }

    @Test(dataProvider = "fixedShapeXSegmentedLegalCastSpecies")
    static <E,F> void shuffleCast(VectorSpecies<E> src, VectorSpecies<F> dst) {
        legal_shuffle_cast_kernel(src, dst);
    }

    @Test(dataProvider = "fixedShapeXSegmentedIllegalCastSpecies")
    static <E,F> void shuffleCastNeg(VectorSpecies<E> src, VectorSpecies<F> dst) {
        illegal_shuffle_cast_kernel(src, dst);
    }

    @Test(dataProvider = "fixedShapeXSegmentedLegalCastSpecies")
    static <E,F> void maskCast(VectorSpecies<E> src, VectorSpecies<F> dst) {
        legal_mask_cast_kernel(src, dst);
    }

    @Test(dataProvider = "fixedShapeXSegmentedIllegalCastSpecies")
    static <E,F> void maskCastNeg(VectorSpecies<E> src, VectorSpecies<F> dst) {
        illegal_mask_cast_kernel(src, dst);
    }
}
