/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @bug     6746184
 * @summary javac fails to compile call to public varargs method
 */

public class T6746184 {
    public static void main(String[] args) {
        A.m(new Object());
    }
}

class A {
    public static void m(final Object... varargs) {}
    private static void m(final Object singleArg) {}
}
