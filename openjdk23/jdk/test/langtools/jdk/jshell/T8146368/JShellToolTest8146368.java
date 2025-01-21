/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8146368
 * @summary Test Smashing Error when user language is Japanese
 * @modules jdk.jshell/jdk.internal.jshell.tool
 * @library /tools/lib /jdk/jshell
 * @build ReplToolTesting
 * @run testng/othervm -Duser.language=ja JShellToolTest8146368
 */

import org.testng.annotations.Test;

@Test
public class JShellToolTest8146368 extends ReplToolTesting {
    public void test() {
        test(
                a -> assertCommand(a, "class A extends B {}", "|  created class A, however, it cannot be referenced until class B is declared\n"),
                a -> assertCommand(a, "und m() { return new und(); }", "|  created method m(), however, it cannot be referenced until class und is declared\n")
        );
    }
}
