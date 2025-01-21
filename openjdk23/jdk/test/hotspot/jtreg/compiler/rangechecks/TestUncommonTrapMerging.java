/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8140574
 * @summary Verify proper re-execution of checks after merging of uncommon traps
 *
 * @run main/othervm -Xcomp -XX:-TieredCompilation
 *                   -XX:CompileCommand=compileonly,compiler.rangechecks.TestUncommonTrapMerging::test*
 *                   compiler.rangechecks.TestUncommonTrapMerging Test1
 * @run main/othervm -XX:CompileCommand=compileonly,compiler.rangechecks.TestUncommonTrapMerging::test*
 *                   compiler.rangechecks.TestUncommonTrapMerging Test2
 */

package compiler.rangechecks;

public class TestUncommonTrapMerging {

    public static void main(String[] args) throws Throwable {
        if (args.length < 1) {
            throw new RuntimeException("Not enough arguments!");
        }
        TestUncommonTrapMerging mytest = new TestUncommonTrapMerging();
        String testcase = args[0];
        if (testcase.equals("Test1")) {
            try {
                // '42' should hit the 'arg > 0' check
                mytest.test(42);

            } catch (OutOfMemoryError e) {
                // expected
            }
        } else if (testcase.equals("Test2")) {
            // Compile test2 with uncommon traps at path 1 and path 2
            for (int i = 0; i < 100_000; i++) {
                mytest.test2(-1, 0);
            }

            // Compile test3 which inlines test2 with uncommon traps at
            // path 1 and path 2. Because test3 always passes 'value = 1',
            // C2 will remove the 'value > 0' check and then merge the two
            // uncommon traps.
            for (int i = 0; i < 100_000; i++) {
                mytest.test3(0);
            }

            // This should return through path 2
            if (!mytest.test3(42)) {
                throw new RuntimeException("test2 returned through wrong path!");
            }
        }
    }

    public void test(int arg) throws Throwable {
        // The following two checks should not be merged if the
        // uncommon trap of the dominating if has 'Reason_unloaded'
        // because we need to re-execute both checks after deopt.
        if (arg < 0) {
            throw new RuntimeException("Should not reach here");
        } else if (arg > 0) {
            throw new OutOfMemoryError();
        }
        throw new RuntimeException("Should not reach here");
    }

    public boolean test2(int arg, int value) {
        if (arg < 0) {
            if (value > 0) {
                // path 1
                return false;
            }
        } else if (arg > 0) {
            // path 2
            return true;
        }
        // path 3
        return false;
    }

    public boolean test3(int arg) {
        int i;
        for (i = 0; i < 1; ++i) { }
        // i == 1
        return test2(arg, i);
    }
}
