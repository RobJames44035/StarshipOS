/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug     7160084
 * @summary javac fails to compile an apparently valid class/interface combination
 */
public class T7160084a {

    static int assertionCount = 0;

    static void assertTrue(boolean cond) {
        assertionCount++;
        if (!cond) {
            throw new AssertionError();
        }
    }

    interface Intf {
       enum MyEnumA {
          AA(""),
          UNUSED("");

          private MyEnumA(String s) { }
       }
    }

    enum MyEnumA implements Intf {
        AA("");

        private MyEnumA(String s) { }
    }

    public static void main(String... args) {
        assertTrue(MyEnumA.values().length == 1);
        assertTrue(Intf.MyEnumA.values().length == 2);
        assertTrue(assertionCount == 2);
    }
}
