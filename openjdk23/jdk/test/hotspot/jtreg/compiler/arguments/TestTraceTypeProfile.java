/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * @test
 * @summary Test running TraceTypeProfile enabled.
 * @run main/othervm -XX:+IgnoreUnrecognizedVMOptions -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+TraceTypeProfile compiler.arguments.TestTraceTypeProfile
 */

package compiler.arguments;

public class TestTraceTypeProfile {

    static public void main(String[] args) {
        System.out.println("Passed");
    }
}
