/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8042251
 * @summary Testing InnerClasses_attribute of inner annotations in inner enum.
 * @library /tools/lib /tools/javac/lib ../lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 *          java.base/jdk.internal.classfile.impl
 * @build toolbox.ToolBox InMemoryFileManager TestResult TestBase
 * @build InnerClassesInInnerClassTestBase InnerClassesTestBase
 * @run main InnerAnnotationsInInnerEnumTest
 */

public class InnerAnnotationsInInnerEnumTest extends InnerClassesInInnerClassTestBase {

    public static void main(String[] args) throws TestFailedException {
        InnerClassesTestBase test = new InnerAnnotationsInInnerEnumTest();
        test.test("InnerClassesSrc$Inner", "Inner", "1");
    }

    @Override
    public void setProperties() {
        setOuterOtherModifiers(Modifier.EMPTY, Modifier.STATIC);
        setInnerOtherModifiers(Modifier.EMPTY, Modifier.STATIC, Modifier.ABSTRACT);
        setOuterClassType(ClassType.ENUM);
        setInnerClassType(ClassType.ANNOTATION);
        setPrefix("Inner {;");
        setSuffix("}");
    }
}
