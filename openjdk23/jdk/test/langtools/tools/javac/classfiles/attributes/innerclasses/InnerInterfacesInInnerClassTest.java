/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @summary Testing InnerClasses_attribute of inner interfaces in inner class.
 * @author aeremeev
 * @library /tools/lib /tools/javac/lib ../lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 *          java.base/jdk.internal.classfile.impl
 * @build toolbox.ToolBox InMemoryFileManager TestResult TestBase
 * @build InnerClassesInInnerClassTestBase InnerClassesTestBase
 * @run main InnerInterfacesInInnerClassTest
 */

public class InnerInterfacesInInnerClassTest extends InnerClassesInInnerClassTestBase {

    public static void main(String[] args) throws TestFailedException {
        InnerClassesTestBase test = new InnerInterfacesInInnerClassTest();
        test.test("InnerClassesSrc$Inner", "Inner", "1");
    }

    @Override
    public void setProperties() {
        setForbiddenWithoutStaticInOuterMods(true);
        setInnerOtherModifiers(Modifier.EMPTY, Modifier.ABSTRACT, Modifier.STATIC);
        setOuterClassType(ClassType.CLASS);
        setInnerClassType(ClassType.INTERFACE);
    }
}
