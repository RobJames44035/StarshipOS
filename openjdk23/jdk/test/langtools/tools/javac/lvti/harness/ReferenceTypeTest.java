/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8177466
 * @summary Add compiler support for local variable type-inference
 * @modules jdk.compiler/com.sun.source.tree
 *          jdk.compiler/com.sun.source.util
 *          jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.code
 *          jdk.compiler/com.sun.tools.javac.util
 * @build LocalVariableInferenceTester
 * @run main LocalVariableInferenceTester ReferenceTypeTest.java
 */
class ReferenceTypeTest {

    static final String STRING = "java.lang.String";
    static final String FOO = "ReferenceTypeTest.Foo";

    void test() {
        @InferredType(STRING)
        var s = "";
        for (@InferredType(STRING) var s2 = "" ; ; ) { break; }
        for (@InferredType(STRING) var s2 : stringArray()) { break; }
        for (@InferredType(STRING) var s2 : stringIterable()) { break; }
        try (@InferredType(FOO) var s2 = new Foo()) { } finally { }
        try (@InferredType(FOO) var s2 = new Foo(); @InferredType(FOO) var s3 = new Foo()) { } finally { }
    }

    String[] stringArray() { return null; }
    Iterable<String> stringIterable() { return null; }

    static class Foo implements AutoCloseable {
        @Override
        public void close() { }
    }
}
