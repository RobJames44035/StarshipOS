/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8044537
 * @summary Checking ACC_SYNTHETIC flag is generated for "this$0" field.
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 * @library /tools/lib /tools/javac/lib ../lib
 * @build toolbox.ToolBox InMemoryFileManager TestResult TestBase
 * @build ThisFieldTest SyntheticTestDriver ExpectedClass ExpectedClasses
 * @run main SyntheticTestDriver ThisFieldTest
 */

import java.util.Objects;

/**
 * Synthetic members:
 * 1. fields this$0 for local and anonymous classes.
 */
@ExpectedClass(className = "ThisFieldTest",
        expectedMethods = "<init>()")
@ExpectedClass(className = "ThisFieldTest$1Local",
        expectedMethods = "<init>(ThisFieldTest)",
        expectedNumberOfSyntheticFields = 1)
@ExpectedClass(className = "ThisFieldTest$1",
        expectedMethods = "<init>(ThisFieldTest)",
        expectedNumberOfSyntheticFields = 1)
public class ThisFieldTest {
    {
        class Local {
            {
                // access enclosing instance so this$0 field is generated
                Objects.requireNonNull(ThisFieldTest.this);
            }
        }

        new Local() {
            {
                // access enclosing instance so this$0 field is generated
                Objects.requireNonNull(ThisFieldTest.this);
            }
        };
    }
}
