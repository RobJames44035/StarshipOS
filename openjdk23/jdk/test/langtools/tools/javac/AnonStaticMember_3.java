/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4279339
 * @summary Verify that an anonymous class can contain static constant.
 * @author maddox
 *
 * @run compile AnonStaticMember_3.java
 */

class AnonStaticMember_1 {
    Object x = new Object() {
        static final int y = 10;
    };
}
