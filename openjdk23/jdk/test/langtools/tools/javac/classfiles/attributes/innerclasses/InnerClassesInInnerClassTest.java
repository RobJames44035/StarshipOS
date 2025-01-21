/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8034854 8042251
 * @summary Testing InnerClasses_attribute of inner classes in inner class.
 * @library /tools/lib /tools/javac/lib ../lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 *          java.base/jdk.internal.classfile.impl
 * @build toolbox.ToolBox InMemoryFileManager TestResult TestBase
 * @build InnerClassesInInnerClassTestBase InnerClassesTestBase
 * @run main InnerClassesInInnerClassTest true
 * @run main InnerClassesInInnerClassTest false
 */

import java.util.Arrays;
import java.util.List;

public class InnerClassesInInnerClassTest extends InnerClassesInInnerClassTestBase {

    final boolean expectSyntheticClass;

    public InnerClassesInInnerClassTest(boolean expectSyntheticClass) {
        this.expectSyntheticClass = expectSyntheticClass;
    }

    public static void main(String[] args) throws TestFailedException {
        boolean expectSyntheticClass = Boolean.parseBoolean(args[0]);
        InnerClassesTestBase test = new InnerClassesInInnerClassTest(expectSyntheticClass);
        test.test("InnerClassesSrc$Inner", "Inner", "1");
    }

    @Override
    public void setProperties() {
        setHasSyntheticClass(expectSyntheticClass);
        setOuterClassType(ClassType.CLASS);
        setInnerClassType(ClassType.CLASS);
    }

    @Override
    public List<TestCase> generateTestCases() {
        setForbiddenWithoutStaticInOuterMods(true);
        List<TestCase> sources = super.generateTestCases();

        setForbiddenWithoutStaticInOuterMods(false);
        setOuterOtherModifiers(Modifier.EMPTY, Modifier.ABSTRACT, Modifier.FINAL);
        setInnerOtherModifiers(Modifier.EMPTY, Modifier.ABSTRACT, Modifier.FINAL);
        sources.addAll(super.generateTestCases());

        return sources;
    }

    @Override
    protected List<String> getCompileOptions() {
        return !expectSyntheticClass ?
                super.getCompileOptions() :
                Arrays.asList("-source", "10", "-target", "10");
    }
}
