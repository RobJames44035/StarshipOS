/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class Neg11 {

    void test() {
        class Foo<X extends Number> { }
        Foo<?> f1 = new UndeclaredName<>(); //this is deliberate: aim is to test erroneous path
        Foo<?> f2 = new UndeclaredName<>() {}; //this is deliberate: aim is to test erroneous path
    }
}
