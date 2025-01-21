/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 8003280
 * @summary Add lambda tests
 *  spurious functional interface conversion errors with default methods in target type
 * @compile TargetType48.java
 */

class TargetType48 {
    interface I1 {
        void a();
        void b();
        void c();
    }

    interface I2 extends I1 {
        default void a() { }
    }

    interface I3 extends I2 {
        default void b() { }
    }

    I3 i3 = ()->{ };
}
