/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8044537
 * @summary Checking ACC_SYNTHETIC flag is generated for access method
 *          generated to access to private methods and fields.
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 * @library /tools/lib /tools/javac/lib ../lib
 * @build toolbox.ToolBox InMemoryFileManager TestResult
 * @build AccessToPrivateInnerClassMembersTest SyntheticTestDriver ExpectedClass ExpectedClasses
 * @run main SyntheticTestDriver AccessToPrivateInnerClassMembersTest
 */

/**
 * Access from top level class to inner classes.
 * Synthetic members:
 * 1. inner classes for Inner*.
 * 2. getter/setter for private field var.
 * 3. access method for private method function().
 * 4. getter/setter for private field staticVar.
 * 5. access method for private method staticFunction().
 * 6. constructor for Inner*.
 */
@ExpectedClass(className = "AccessToPrivateInnerClassMembersTest",
        expectedMethods = {"<init>()", "<clinit>()"})
@ExpectedClass(className = "AccessToPrivateInnerClassMembersTest$Inner1",
        expectedMethods = {"<init>(AccessToPrivateInnerClassMembersTest)", "function()"},
        expectedFields = "var")
@ExpectedClass(className = "AccessToPrivateInnerClassMembersTest$Inner2",
        expectedMethods = {"function()", "staticFunction()", "<init>()"},
        expectedFields = {"staticVar", "var"})
public class AccessToPrivateInnerClassMembersTest {

    private class Inner1 {
        private Inner1() {}
        private int var;
        private void function() {}
    }

    {
        Inner1 inner = new Inner1();
        inner.var = 0;
        int i = inner.var;
        inner.function();
    }

    private static class Inner2 {
        private Inner2() {}
        private int var;
        private static int staticVar;
        private void function() {}
        private static void staticFunction() {}
    }

    {
        Inner2 inner = new Inner2();
        inner.var = 0;
        int i = inner.var;
        inner.function();
    }

    static {
        Inner2.staticFunction();
        Inner2.staticVar = 0;
        int i = Inner2.staticVar;
    }
}
