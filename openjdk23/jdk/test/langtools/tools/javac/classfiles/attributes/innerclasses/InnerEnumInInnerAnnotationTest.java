/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8042251
 * @summary Testing InnerClasses_attribute of inner enums in inner annotation.
 * @library /tools/lib /tools/javac/lib ../lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 *          java.base/jdk.internal.classfile.impl
 * @build toolbox.ToolBox InMemoryFileManager TestResult TestBase
 * @build InnerClassesInInnerClassTestBase InnerClassesTestBase
 * @run main InnerEnumInInnerAnnotationTest
 */

public class InnerEnumInInnerAnnotationTest extends InnerClassesInInnerClassTestBase {

    public static void main(String[] args) throws TestFailedException {
        InnerClassesTestBase test = new InnerEnumInInnerAnnotationTest();
        test.test("InnerClassesSrc$Inner", "Inner", "1");
    }

    @Override
    public void setProperties() {
        setOuterOtherModifiers(Modifier.EMPTY, Modifier.ABSTRACT, Modifier.STATIC);
        setInnerAccessModifiers(Modifier.EMPTY, Modifier.PUBLIC);
        setInnerOtherModifiers(Modifier.EMPTY, Modifier.STATIC);
        setOuterClassType(ClassType.ANNOTATION);
        setInnerClassType(ClassType.ENUM);
    }
}
