/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package pkg1;

/**
 * <pre>
 *  Result:  {@value pkg1.Class1#TEST_7_PASSES}
 *  Result:  {@value pkg2.Class3#TEST_12_PASSES}
 * </pre>
 */
public class Class2 {

    /**
     * <pre>
     * Result:  {@value pkg1.Class1#TEST_8_PASSES}
     * Result:  {@value pkg2.Class3#TEST_13_PASSES}
     * </pre>
     */
    public int field;

    /**
     * <pre>
     * Result:  {@value pkg1.Class1#TEST_9_PASSES}
     * Result:  {@value pkg2.Class3#TEST_14_PASSES}
     * </pre>
     */
    public Class2() {}

    /**
     * <pre>
     * Result:  {@value pkg1.Class1#TEST_10_PASSES}
     * Result:  {@value pkg2.Class3#TEST_15_PASSES}
     * </pre>
     */
    public void method() {}

    /**
     * <pre>
     * Result:  {@value pkg1.Class1#TEST_11_PASSES}
     * Result:  {@value pkg2.Class3#TEST_16_PASSES}
     * </pre>
     */
    public class NestedClass{}
}
