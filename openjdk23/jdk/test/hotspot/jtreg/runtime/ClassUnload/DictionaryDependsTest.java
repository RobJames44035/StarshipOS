/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test DictionaryDependsTest
 * @bug 8210094
 * @summary Create ClassLoader dependency from initiating loader to class loader through reflection
 * @requires vm.opt.final.ClassUnloading
 * @modules java.base/jdk.internal.misc
 *          java.compiler
 * @library /test/lib
 * @build jdk.test.whitebox.WhiteBox
 * @compile p2/c2.java MyDiffClassLoader.java
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -Xmn8m -XX:+UnlockDiagnosticVMOptions -Xlog:class+unload -XX:+WhiteBoxAPI DictionaryDependsTest
 */
import jdk.test.whitebox.WhiteBox;
import java.lang.reflect.Method;
import jdk.test.lib.classloader.ClassUnloadCommon;
import java.util.List;
import java.util.Set;

public class DictionaryDependsTest {
    public static WhiteBox wb = WhiteBox.getWhiteBox();
    public static final String MY_TEST = "DictionaryDependsTest$c1r";

    static public class c1r {

        private void test() throws Exception {
            // forName loads through reflection and doesn't create dependency
            Class<?> x = Class.forName("p2.c2", true, c1r.class.getClassLoader());
            Method m = x.getMethod("method2");
            java.lang.Object t = x.newInstance();
            m.invoke(t);
        }

        public c1r () throws Exception {
            test();
            ClassUnloadCommon.triggerUnloading();  // should unload p2.c2
            test();
            ClassUnloadCommon.triggerUnloading();  // should unload p2.c2
        }
    }

    public void test() throws Throwable {

        // now use the same loader to load class MyTest
        Class MyTest_class = new MyDiffClassLoader(MY_TEST).loadClass(MY_TEST);

        try {
            // Call MyTest to load p2.c2 twice and call p2.c2.method2
            MyTest_class.newInstance();
        } catch (Exception e) {
            System.out.println("Not expected NSME");
            throw new RuntimeException("Not expecting NSME");
        }
        ClassUnloadCommon.triggerUnloading();  // should not unload anything
        ClassUnloadCommon.failIf(!wb.isClassAlive(MY_TEST), "should not be unloaded");
        ClassUnloadCommon.failIf(!wb.isClassAlive("p2.c2"), "should not be unloaded");
        // Unless MyTest_class is referenced here, the compiler can unload it.
        System.out.println("Should not unload anything before here because " + MyTest_class + " is still alive.");
    }

    public static void main(String args[]) throws Throwable {
        DictionaryDependsTest d = new DictionaryDependsTest();
        d.test();

        // Now unload MY_TEST and p2.c2
        Set<String> aliveClasses = ClassUnloadCommon.triggerUnloading(List.of(MY_TEST, "p2.c2"));
        ClassUnloadCommon.failIf(!aliveClasses.isEmpty(), "should be unloaded: " + aliveClasses);
    }
}
