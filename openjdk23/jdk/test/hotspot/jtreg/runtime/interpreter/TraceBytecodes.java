/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8309811
 * @requires vm.debug
 * @summary Test the output of -XX:+TraceBytecodes, -XX:TraceBytecodesAt, and -XX:TraceBytecodesStopAt
 * @run main/othervm -XX:+TraceBytecodes -XX:TraceBytecodesAt=2000 -XX:TraceBytecodesStopAt=3000 TraceBytecodes
 */

// This is just a very simple sanity test. Trace about 1000 bytecodes. See the .jtr file for the output.
// Consider it OK if the VM doesn't crash. It should test a fair amount of the code in bytecodeTracer.cpp
public class TraceBytecodes {
    public static void main(String args[]) {
        System.out.println("Hello TraceBytecodes");
    }
}
