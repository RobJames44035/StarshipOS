/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug     6302214
 * @summary erroneus "inconvertible types" error
 * @author  Peter von der Ah\u00e9
 * @compile -Xlint:unchecked -Werror T6302214a.java
 */

public class T6302214a {
    class X<T> {}
    class X2 extends X<String> {}
    class Test {
        X<? extends Object> x;
        X2 x2 = (X2)x;
    }
}
