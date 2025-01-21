/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public @interface AnnoWithClinitFail {
    Foo f = new Foo();

    String foo();
    String bar() default "bar";

    @AnnoWithClinitFail
    static class C {} // this is in the same CU so there wont be a
                      // <clinit> when the this anno instance is checked

    class Foo {}
}

@AnnoWithClinitFail
class TestAnnoWithClinitFail { }
