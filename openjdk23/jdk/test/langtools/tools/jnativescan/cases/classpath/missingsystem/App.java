/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
package missingsystem;

public class App {
    public static void main(String[] args) {
        // this class was present in Java 20, but removed in 21
        // if we compile with --release 20, but run jnativescan
        // with --release 21, we should get an error
        java.lang.Compiler.enable();
    }
}
