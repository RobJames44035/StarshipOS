/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
/*
 * @test
 * @bug 8291657
 * @summary Javac assertion when compiling a method call with switch expression as argument
 * @compile T8291657.java
 */
public class T8291657 {
    static class A { }
    interface B { }
    static void f(final B b) { }

    static public B minimized(Object o) {
        return (B) switch (o) {
            default -> new A();
        };
    }

    public static void main(final String... args) {
        f((B) switch (new Object()) {
            case Object obj -> new A() {};
        });
    }
}
