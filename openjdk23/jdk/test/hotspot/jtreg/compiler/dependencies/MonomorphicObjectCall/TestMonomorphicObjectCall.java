/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8050079
 * @summary Compiles a monomorphic call to finalizeObject() on a modified java.lang.Object to test C1 CHA.
 *
 * @build java.base/java.lang.Object
 * @run main/othervm -XX:+IgnoreUnrecognizedVMOptions -Xcomp -XX:-VerifyDependencies
 *                   -XX:TieredStopAtLevel=1
 *                   -XX:CompileCommand=compileonly,compiler.dependencies.MonomorphicObjectCall.TestMonomorphicObjectCall::callFinalize
 *                   -XX:CompileCommand=compileonly,java.lang.Object::finalizeObject
 *                   compiler.dependencies.MonomorphicObjectCall.TestMonomorphicObjectCall
 */

package compiler.dependencies.MonomorphicObjectCall;

public class TestMonomorphicObjectCall {

    private static void callFinalize(Object object) throws Throwable {
        // Call modified version of java.lang.Object::finalize() that is
        // not overridden by any subclass. C1 CHA should mark the call site
        // as monomorphic and inline the method.
        object.finalizeObject();
    }

    public static void main(String[] args) throws Throwable {
        // Trigger compilation of 'callFinalize'
        callFinalize(new Object());
    }
}
