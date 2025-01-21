/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8044537
 * @summary Checking ACC_SYNTHETIC flag is generated for enum members.
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 * @library /tools/lib /tools/javac/lib ../lib
 * @build toolbox.ToolBox InMemoryFileManager TestResult TestBase
 * @build EnumTest SyntheticTestDriver ExpectedClass ExpectedClasses
 * @run main SyntheticTestDriver EnumTest
 */

/**
 * Synthetic members:
 * 1. field $VALUES.
 */
@ExpectedClass(className = "EnumTest",
        expectedMethods = {"values()", "valueOf(java.lang.String)", "<clinit>()", "<init>(java.lang.String, int)"},
        expectedFields = {"A", "B"},
        expectedNumberOfSyntheticMethods = 1,
        expectedNumberOfSyntheticFields = 1)
public enum EnumTest {
    A, B
}
