/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8034854 8042251
 * @summary Testing InnerClasses_attribute of inner classes in inner enum.
 * @library /tools/lib /tools/javac/lib ../lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 *          java.base/jdk.internal.classfile.impl
 * @build toolbox.ToolBox InMemoryFileManager TestResult TestBase
 * @build InnerClassesInInnerClassTestBase InnerClassesTestBase
 * @run main InnerClassesInInnerEnumTest true
 * @run main InnerClassesInInnerEnumTest false
 */

import java.util.Arrays;
import java.util.List;

public class InnerClassesInInnerEnumTest extends InnerClassesInInnerClassTestBase {

    final boolean expectSyntheticClass;

    public InnerClassesInInnerEnumTest(boolean expectSyntheticClass) {
        this.expectSyntheticClass = expectSyntheticClass;
    }

    public static void main(String[] args) throws TestFailedException {
        boolean expectSyntheticClass = Boolean.parseBoolean(args[0]);
        InnerClassesTestBase test = new InnerClassesInInnerEnumTest(expectSyntheticClass);
        test.test("InnerClassesSrc$Inner", "Inner", "1");
    }

    @Override
    public void setProperties() {
        setOuterOtherModifiers(Modifier.EMPTY, Modifier.STATIC);
        setOuterClassType(ClassType.ENUM);
        setInnerClassType(ClassType.CLASS);
        setHasSyntheticClass(expectSyntheticClass);
        setPrefix("Inner {;");
        setSuffix("}");
    }

    @Override
    protected List<String> getCompileOptions() {
        return !expectSyntheticClass ?
                super.getCompileOptions() :
                Arrays.asList("-source", "10", "-target", "10");
    }
}
