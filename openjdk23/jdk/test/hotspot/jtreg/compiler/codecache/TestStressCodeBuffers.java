/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8257513
 * @requires vm.debug == true
 * @summary Stress testing code buffers resulted in an assertion failure due to not taking expand calls into account
 *          which can fail more often with -XX:+StressCodeBuffers. Perform some more sanity flag testing.
 * @run main/othervm -Xcomp -XX:-TieredCompilation -XX:+StressCodeBuffers compiler.codecache.TestStressCodeBuffers
 * @run main/othervm -Xcomp -XX:+StressCodeBuffers compiler.codecache.TestStressCodeBuffers
 */

package compiler.codecache;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class TestStressCodeBuffers {

    static MethodHandle permh;

    public static void main(String[] args) throws Exception {
        test();
    }

    public static void test() throws Exception {
        MethodHandles.Lookup lookup = MethodHandles.publicLookup();
        MethodHandle mh = lookup.findStatic(TestStressCodeBuffers.class, "bar",
                                            MethodType.methodType(void.class, int.class, long.class));
        permh = MethodHandles.permuteArguments(mh, mh.type(), 0, 1); // Triggers assertion failure
    }

    public static void bar(int x, long y) {}
}

