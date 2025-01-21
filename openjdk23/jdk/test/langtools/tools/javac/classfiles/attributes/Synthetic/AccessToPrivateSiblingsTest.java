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
 * @build toolbox.ToolBox InMemoryFileManager TestResult TestBase
 * @build AccessToPrivateSiblingsTest SyntheticTestDriver ExpectedClass ExpectedClasses
 * @run main SyntheticTestDriver AccessToPrivateSiblingsTest
 */

/**
 * Access from sibling classes to sibling classes.
 * Synthetic members:
 * 1. inner classes for Inner*.
 * 2. getter/setter for private field var.
 * 3. access method for private method function().
 * 4. getter/setter for private field staticVar.
 * 5. access method for private method staticFunction().
 * 6. constructor for Inner*.
 */
@ExpectedClass(className = "AccessToPrivateSiblingsTest", expectedMethods = "<init>()")
@ExpectedClass(className = "AccessToPrivateSiblingsTest$Inner1",
        expectedMethods = {"function()", "<init>(AccessToPrivateSiblingsTest)"},
        expectedFields = "var")
@ExpectedClass(className = "AccessToPrivateSiblingsTest$Inner2",
        expectedMethods = "<init>(AccessToPrivateSiblingsTest)",
        expectedNumberOfSyntheticFields = 1)
@ExpectedClass(className = "AccessToPrivateSiblingsTest$Inner3",
        expectedMethods = {"<init>()", "function()", "staticFunction()", "<clinit>()"},
        expectedFields = {"var", "staticVar"})
@ExpectedClass(className = "AccessToPrivateSiblingsTest$Inner4",
        expectedMethods = {"<init>()", "function()", "staticFunction()"},
        expectedFields = {"var", "staticVar"})
public class AccessToPrivateSiblingsTest {

    private class Inner1 {
        private Inner1() {}
        private int var;
        private void function() {}

        {
            Inner3 inner = new Inner3();
            inner.var = 0;
            int i = inner.var;
            inner.function();
        }
    }

    private class Inner2 {
        {
            Inner1 inner = new Inner1();
            inner.var = 0;
            int i = inner.var;
            inner.function();
        }
    }

    private static class Inner3 {
        private Inner3() {}
        private int var;
        private static int staticVar;
        private void function() {}
        private static void staticFunction() {}

        static {
            Inner4 inner = new Inner4();
            inner.var = 0;
            int i = inner.var;
            inner.function();
        }
    }

    private static class Inner4 {
        private Inner4() {}
        private int var;
        private static int staticVar;
        private void function() {}
        private static void staticFunction() {}
    }
}
