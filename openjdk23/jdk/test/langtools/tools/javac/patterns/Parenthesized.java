/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8262891 8269354
 * @summary Test parenthesized pattern
 * @compile/fail/ref=Parenthesized.out -XDrawDiagnostics Parenthesized.java
 */
public class Parenthesized {
    public static void main(String... args) {
        new Parenthesized().run();
    }

    void run() {
        Object o = "";
        switch (o) {
            case (String s) when s.isEmpty() -> System.err.println("OK: " + s);
            default -> throw new AssertionError();
        }
        System.err.println(switch (o) {
            case (String s) when s.isEmpty() -> "OK: " + s;
            default -> throw new AssertionError();
        });
        if (o instanceof (String s) && s.isEmpty()) {
            System.err.println("OK: " + s);
        }
        boolean b1 = o instanceof (String s) && s.isEmpty();
        boolean b2 = o instanceof String s && s.isEmpty();
    }

}
