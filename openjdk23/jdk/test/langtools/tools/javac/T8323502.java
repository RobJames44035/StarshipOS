/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
/*
 * @test
 * @bug 8323502
 * @summary javac crash with wrongly typed method block in Flow
 * @compile/fail/ref=T8323502.out -XDrawDiagnostics --should-stop=at=FLOW -XDdev T8323502.java
 */
public class T8323502 {
    public void m(Object o) {
        return switch(o) {
            default -> System.out.println("boom");
        };
    }
}
