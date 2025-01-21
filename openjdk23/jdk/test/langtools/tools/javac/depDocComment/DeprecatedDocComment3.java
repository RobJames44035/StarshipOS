/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 7096014
 * @summary Javac tokens should retain state
 * @compile -Xlint:-dangling-doc-comments -Werror DeprecatedDocComment3.java
 */

class DeprecatedDocComment3 {
    static class Foo { }

    ; /** @deprecated */ ;

    static class A {}

    static class B {
       A a; //not deprecated!
    }
}
