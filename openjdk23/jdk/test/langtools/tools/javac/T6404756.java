/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public class T6404756 {
    public void foo(Foo f) {
        @Deprecated String s1 = f.foo;
    }

}

class Foo {
    @Deprecated String foo;
}
