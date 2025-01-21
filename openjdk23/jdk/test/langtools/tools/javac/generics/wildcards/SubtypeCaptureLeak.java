/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8039214
 * @summary Capture variables used for subtyping should not leak out of inference
 * @compile SubtypeCaptureLeak.java
 */

public class SubtypeCaptureLeak {

    interface Parent<T> {}
    interface Child<T> extends Parent<T> {}
    interface Box<T> {}
    interface SubBox<T> extends Box<T> {}

    // applicability inference

    <T> void m1(Parent<? extends T> arg) {}

    void testApplicable(Child<?> arg) {
        m1(arg);
    }

    // applicability inference, nested

    <T> void m2(Box<? extends Parent<? extends T>> arg) {}

    void testApplicable(Box<Child<?>> arg) {
        m2(arg);
    }

    // most specific inference

    <T> void m3(Parent<? extends T> arg) {}
    void m3(Child<?> arg) {}

    void testMostSpecific(Child<?> arg) {
        m3(arg);
    }

    // most specific inference, nested

    <T> void m4(Box<? extends Parent<? extends T>> arg) {}
    void m4(SubBox<Child<?>> arg) {}

    void testMostSpecificNested(SubBox<Child<?>> arg) {
        m4(arg);
    }

}
