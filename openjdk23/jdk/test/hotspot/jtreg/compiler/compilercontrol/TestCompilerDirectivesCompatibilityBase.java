/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test TestCompilerDirectivesCompatibilityBase
 * @bug 8137167
 * @summary Test compiler control compatibility with compile command
 * @library /test/lib /
 * @modules java.base/jdk.internal.misc
 *          java.compiler
 *          java.management
 *
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run testng/othervm -Xbootclasspath/a:. -Xmixed -XX:+UnlockDiagnosticVMOptions
 *      -XX:+WhiteBoxAPI
 *      compiler.compilercontrol.TestCompilerDirectivesCompatibilityBase
 */

package compiler.compilercontrol;

import compiler.testlibrary.CompilerUtils;
import compiler.whitebox.CompilerWhiteBoxTest;
import jdk.test.lib.dcmd.CommandExecutor;
import jdk.test.lib.dcmd.JMXExecutor;
import org.testng.annotations.Test;
import jdk.test.whitebox.WhiteBox;

import java.io.File;
import java.lang.reflect.Method;

public class TestCompilerDirectivesCompatibilityBase {

    public static final WhiteBox WB = WhiteBox.getWhiteBox();
    public static String control_on, control_off;
    Method method, nomatch;

    public void run(CommandExecutor executor) throws Exception {

        control_on = System.getProperty("test.src", ".") + File.separator + "control_on.txt";
        control_off = System.getProperty("test.src", ".") + File.separator + "control_off.txt";
        method  = getMethod(TestCompilerDirectivesCompatibilityBase.class, "helper");
        nomatch = getMethod(TestCompilerDirectivesCompatibilityBase.class, "another");

        int[] levels = CompilerUtils.getAvailableCompilationLevels();
        for (int complevel : levels) {
            // Only test the major compilers, ignore profiling levels
            if (complevel == CompilerWhiteBoxTest.COMP_LEVEL_SIMPLE || complevel == CompilerWhiteBoxTest.COMP_LEVEL_FULL_OPTIMIZATION){
                testCompatibility(executor, complevel);
            }
        }
    }

    public void testCompatibility(CommandExecutor executor, int comp_level) throws Exception {

        // Call all validation twice to catch error when overwriting a directive
        // Flag is default off
        expect(!WB.getBooleanVMFlag("PrintAssembly"));
        expect(!WB.shouldPrintAssembly(method, comp_level));
        expect(!WB.shouldPrintAssembly(nomatch, comp_level));
        expect(!WB.shouldPrintAssembly(method, comp_level));
        expect(!WB.shouldPrintAssembly(nomatch, comp_level));

        // load directives that turn it on
        executor.execute("Compiler.directives_add " + control_on);
        expect(WB.shouldPrintAssembly(method, comp_level));
        expect(!WB.shouldPrintAssembly(nomatch, comp_level));
        expect(WB.shouldPrintAssembly(method, comp_level));
        expect(!WB.shouldPrintAssembly(nomatch, comp_level));

        // remove and see that it is true again
        executor.execute("Compiler.directives_remove");
        expect(!WB.shouldPrintAssembly(method, comp_level));
        expect(!WB.shouldPrintAssembly(nomatch, comp_level));
        expect(!WB.shouldPrintAssembly(method, comp_level));
        expect(!WB.shouldPrintAssembly(nomatch, comp_level));
    }

    public void expect(boolean test) throws Exception {
        if (!test) {
            throw new Exception("Test failed");
        }
    }

    public void expect(boolean test, String msg) throws Exception {
        if (!test) {
            throw new Exception(msg);
        }
    }

    @Test
    public void jmx() throws Exception {
        run(new JMXExecutor());
    }

    public void helper() {
    }

    public void another() {
    }

    public static Method getMethod(Class klass, String name, Class<?>... parameterTypes) {
        try {
            return klass.getDeclaredMethod(name, parameterTypes);
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException("exception on getting method Helper." + name, e);
        }
    }
}
