/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @modules jdk.compiler/com.sun.tools.javac.code
 *          jdk.compiler/com.sun.tools.javac.tree
 *          jdk.compiler/com.sun.tools.javac.util
 * @build DA TA Test TestProcessor
 * @compile -XDaccessInternalAPI -proc:only -processor TestProcessor AnnoTreeTests.java
 */

@Test(4)
class AnnoTreeTests {
    // primitive types
    // @TA("int") int i1 = 0; // TODO: Only visible via ClassFile
    long i2 = (@TA("long") long) 0;

    // simple array types
    // @DA("short") short[] a1; // TODO: Only visible via ClassFile
    byte @TA("byte[]") [] a2;
    float[] a3 = (@TA("float") float[]) null;
    double[] a4 = (double @TA("double[]") []) null;

    // multi-dimensional array types
    // (still to come)
}
