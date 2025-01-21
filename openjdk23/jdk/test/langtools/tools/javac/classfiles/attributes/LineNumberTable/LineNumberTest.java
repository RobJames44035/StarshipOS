/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @summary Tests a line number table attribute for language constructions in different containers.
 * @bug 8040131
 * @library /tools/lib /tools/javac/lib ../lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 *          jdk.compiler/com.sun.tools.javac.util
 *          java.base/jdk.internal.classfile.impl
 * @build toolbox.ToolBox InMemoryFileManager TestBase
 * @build LineNumberTestBase Container TestCase
 * @run main LineNumberTest
 */
public class LineNumberTest extends LineNumberTestBase {
    public static void main(String[] args) throws Exception {
        new LineNumberTest().test();
    }

    public void test() throws Exception {
        int failed = 0;
        for (TestData testData : TestData.values()) {
            echo("[Executing test]: " + testData);
            try {
                test(testData.container);
            } catch (Exception e) {
                echo("[Test failed]: " + testData);
                e.printStackTrace();
                failed++;
                continue;
            }
            echo("[Test passed]: " + testData);
        }
        if (failed > 0)
            throw new RuntimeException(String.format("Failed tests %d of %d%n", failed, TestData.values().length));
    }

    enum TestData {
        SimpleMethod(new MainContainer()),
        LocalClassContainer(new MainContainer()
                .insert(new LocalClassContainer())),
        LambdaContainer(new MainContainer()
                .insert(new LambdaContainer())),
        LambdaInLambdaContainer(new MainContainer()
                .insert(new LambdaContainer())
                .insert(new LambdaContainer())),
        LambdaInLocalClassContainerTest(new MainContainer()
                .insert(new LocalClassContainer())
                .insert(new LambdaContainer())),
        LocalClassInLambdaContainer(new MainContainer()
                .insert(new LambdaContainer())
                .insert(new LocalClassContainer())),
        LocalInLocalContainer(new MainContainer()
                .insert(new LocalClassContainer())
                .insert(new LocalClassContainer()));
        Container container;

        TestData(Container container) {
            this.container = container;
        }
    }
}
