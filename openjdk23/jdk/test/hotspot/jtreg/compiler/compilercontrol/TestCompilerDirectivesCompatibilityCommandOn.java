/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test TestCompilerDirectivesCompatibilityCommandOn
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
 *      -XX:-PrintAssembly -XX:CompileCommand=print,*.* -XX:+WhiteBoxAPI
 *      compiler.compilercontrol.TestCompilerDirectivesCompatibilityCommandOn
 */

package compiler.compilercontrol;

import jdk.test.lib.dcmd.CommandExecutor;

public class TestCompilerDirectivesCompatibilityCommandOn extends TestCompilerDirectivesCompatibilityBase {

    public void testCompatibility(CommandExecutor executor, int comp_level) throws Exception {

        // Call all validation twice to catch error when overwriting a directive
        // Flag is default on
        expect(WB.shouldPrintAssembly(method, comp_level));
        expect(WB.shouldPrintAssembly(nomatch, comp_level));
        expect(WB.shouldPrintAssembly(method, comp_level));
        expect(WB.shouldPrintAssembly(nomatch, comp_level));

        // load directives that turn it off
        executor.execute("Compiler.directives_add " + control_off);
        expect(!WB.shouldPrintAssembly(method, comp_level));
        expect(WB.shouldPrintAssembly(nomatch, comp_level));
        expect(!WB.shouldPrintAssembly(method, comp_level));
        expect(WB.shouldPrintAssembly(nomatch, comp_level));

        // remove and see that it is true again
        executor.execute("Compiler.directives_remove");
        expect(WB.shouldPrintAssembly(method, comp_level));
        expect(WB.shouldPrintAssembly(nomatch, comp_level));
        expect(WB.shouldPrintAssembly(method, comp_level));
        expect(WB.shouldPrintAssembly(nomatch, comp_level));
    }
}
