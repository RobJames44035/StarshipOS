/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8316964
 * @summary check exit code in jarsigner and keytool
 * @library /test/lib
 * @modules java.base/sun.security.tools.keytool
 *          jdk.jartool/sun.security.tools.jarsigner
 */

import jdk.test.lib.Asserts;
import jdk.test.lib.SecurityTools;

public class ExitOrNot {
    public static void main(String[] args) throws Exception {

        // launching the tool still exits
        SecurityTools.jarsigner("1 2 3")
                .shouldHaveExitValue(1);
        SecurityTools.keytool("-x")
                .shouldHaveExitValue(1);

        // calling the run() methods no longer
        Asserts.assertEQ(new sun.security.tools.jarsigner.Main()
                    .run("1 2 3".split(" ")), 1);

        Asserts.assertEQ(new sun.security.tools.keytool.Main()
                    .run("-x".split(" "), System.out), 1);
    }
}
