/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test SuperDependsTest
 * @bug 8210094
 * @summary Create ClassLoader dependency from initiating loader to class loader through subclassing
 * @requires vm.opt.final.ClassUnloading
 * @modules java.base/jdk.internal.misc
 *          java.compiler
 * @library /test/lib
 * @build jdk.test.whitebox.WhiteBox
 * @compile p2/c2.java MyDiffClassLoader.java
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -Xmn8m -XX:+UnlockDiagnosticVMOptions -Xlog:class+unload -XX:+WhiteBoxAPI SuperDependsTest
 */

import jdk.test.whitebox.WhiteBox;
import p2.*;
import jdk.test.lib.classloader.ClassUnloadCommon;
import java.util.List;
import java.util.Set;

public class SuperDependsTest {
    public static WhiteBox wb = WhiteBox.getWhiteBox();
    public static final String MY_TEST = "SuperDependsTest$c1s";


    // p2.c2 loads through super class and creates dependency
    public static class c1s extends p2.c2 {

        private void test() throws Exception {
            method2();
        }

        public c1s () throws Exception {
            test();
            ClassUnloadCommon.triggerUnloading();  // should not unload anything
            test();
        }
    }

    public void test() throws Throwable {

        // now use the same loader to load class MyTest
        Class MyTest_class = new MyDiffClassLoader(MY_TEST).loadClass(MY_TEST);

        // Call MyTest to load p2.c2 twice and call p2.c2.method2
        MyTest_class.newInstance();
        ClassUnloadCommon.triggerUnloading();  // should not unload anything
        ClassUnloadCommon.failIf(!wb.isClassAlive(MY_TEST), "should not be unloaded");
        ClassUnloadCommon.failIf(!wb.isClassAlive("p2.c2"), "should not be unloaded");
        // Unless MyTest_class is referenced here, the compiler can unload it.
        System.out.println("Should not unload anything before here because " + MyTest_class + " is still alive.");
    }

    public static void main(String args[]) throws Throwable {
        SuperDependsTest d = new SuperDependsTest();
        d.test();
        Set<String> aliveClasses = ClassUnloadCommon.triggerUnloading(List.of(MY_TEST, "p2.c2"));
        ClassUnloadCommon.failIf(!aliveClasses.isEmpty(), "should be unloaded: " + aliveClasses);
    }
}
