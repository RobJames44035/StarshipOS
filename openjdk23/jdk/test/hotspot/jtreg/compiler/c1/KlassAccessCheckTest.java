/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8293044
 * @requires vm.compiler1.enabled
 * @compile KlassAccessCheckPackagePrivate.jasm
 * @compile KlassAccessCheck.jasm
 * @run main/othervm -Xbatch -XX:TieredStopAtLevel=1 compiler.c1.KlassAccessCheckTest
 */

package compiler.c1;

public class KlassAccessCheckTest {
    static void test(Runnable r) {
        for (int i = 0; i < 1000; ++i) {
            try {
                r.run();
                throw new AssertionError("No IllegalAccessError thrown");
            } catch (IllegalAccessError e) {
                // Expected
            } catch (AssertionError e) {
                throw e; // rethrow
            } catch (Throwable e) {
                throw new AssertionError("Wrong exception thrown", e);
            }
        }
    }

    public static void main(String[] args) {
        test(() -> KlassAccessCheck.testNewInstance());
        test(() -> KlassAccessCheck.testNewArray());
        test(() -> KlassAccessCheck.testMultiNewArray());
        test(() -> KlassAccessCheck.testCheckCast(42));
        test(() -> KlassAccessCheck.testCheckCastArr(new Integer[0]));
        test(() -> KlassAccessCheck.testInstanceOf(42));
        test(() -> KlassAccessCheck.testInstanceOfArr(new Integer[0]));
        System.out.println("TEST PASSED");
    }
}
