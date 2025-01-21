/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 6894807
 * @summary No ClassCastException for HashAttributeSet constructors if run with -Xcomp
 *
 * @run main compiler.c2.IsInstanceTest
*/

package compiler.c2;

public class IsInstanceTest {

    public static void main(String[] args) {
        BaseInterface baseInterfaceImpl = new BaseInterfaceImpl();
        for (int i = 0; i < 100000; i++) {
            if (isInstanceOf(baseInterfaceImpl, ExtendedInterface.class)) {
                throw new AssertionError("Failed at index:" + i);
            }
        }
        System.out.println("Done!");
    }

    public static boolean isInstanceOf(BaseInterface baseInterfaceImpl, Class... baseInterfaceClasses) {
        for (Class baseInterfaceClass : baseInterfaceClasses) {
            if (baseInterfaceClass.isInstance(baseInterfaceImpl)) {
                return true;
            }
        }
        return false;
    }

    private interface BaseInterface {
    }

    private interface ExtendedInterface extends BaseInterface {
    }

    private static class BaseInterfaceImpl implements BaseInterface {
    }
}
