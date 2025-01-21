/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @key stress randomness
 * @bug 8252219 8256535 8317349 8319879 8335334
 * @requires vm.compiler2.enabled
 * @summary Tests that different combinations of stress options and
 *          -XX:StressSeed=N are accepted.
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+StressIGVN
 *      compiler.arguments.TestStressOptions
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+StressIGVN -XX:StressSeed=42
 *      compiler.arguments.TestStressOptions
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+StressCCP
 *      compiler.arguments.TestStressOptions
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+StressCCP -XX:StressSeed=42
 *      compiler.arguments.TestStressOptions
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+StressLCM
 *      compiler.arguments.TestStressOptions
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+StressLCM -XX:StressSeed=42
 *      compiler.arguments.TestStressOptions
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+StressGCM
 *      compiler.arguments.TestStressOptions
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+StressGCM -XX:StressSeed=42
 *      compiler.arguments.TestStressOptions
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+StressMacroExpansion
 *      compiler.arguments.TestStressOptions
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+StressMacroExpansion -XX:StressSeed=42
 *      compiler.arguments.TestStressOptions
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+StressIncrementalInlining
 *      compiler.arguments.TestStressOptions
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+StressIncrementalInlining -XX:StressSeed=42
 *      compiler.arguments.TestStressOptions
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+StressUnstableIfTraps
 *      compiler.arguments.TestStressOptions
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+StressUnstableIfTraps -XX:StressSeed=42
 *      compiler.arguments.TestStressOptions
 */

package compiler.arguments;

public class TestStressOptions {

    static public void main(String[] args) {
        System.out.println("Passed");
    }
}
