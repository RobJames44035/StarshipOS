/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8203435
 * @summary Test JVMs 5.4.3.6 with respect to a dynamically-computed constant and circularity.
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @compile CondyNestedResolution.jcod
 * @run main/othervm CondyNestedResolutionTest
 */

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.compiler.InMemoryJavaCompiler;

/*
 * JVMs section 5.4.3.6 Dynamically-Computed Constant and Call Site Resolution
 *     "Let X be the symbolic reference currently being resolved, and let Y be a static argument of X
 *      to be resolved as described above. If X and Y are both dynamically-computed constants, and if Y
 *      is either the same as X or has a static argument that references X through its static arguments,
 *      directly or indirectly, resolution fails with a StackOverflowError.
 */
public class CondyNestedResolutionTest {
    public static void main(String args[]) throws Throwable {
        ProcessBuilder pb = ProcessTools.createTestJavaProcessBuilder("CondyNestedResolution");
        OutputAnalyzer oa = new OutputAnalyzer(pb.start());
        oa.shouldContain("StackOverflowError");
        oa.shouldContain("bsm1arg");
        oa.shouldContain("hello1");
        oa.shouldContain("hello2");
        oa.shouldContain("hello4");
        oa.shouldContain("hello6");
        oa.shouldNotContain("hello3");
        oa.shouldNotContain("hello5");
        oa.shouldNotContain("bsm2arg");
        oa.shouldNotContain("bsm3arg");
        oa.shouldNotContain("bsm4arg");
        oa.shouldHaveExitValue(1);
    }
}
