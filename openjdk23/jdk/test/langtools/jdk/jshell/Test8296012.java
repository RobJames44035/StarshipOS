/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8296012
 * @summary jshell crashes on mismatched record pattern
 * @build KullaTesting TestingInputStream
 * @run testng Test8296012
 */

import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

@Test
public class Test8296012 extends KullaTesting {

    public void test() {
        assertEval("record Foo(int x, int y) {}");
        assertEvalFail("switch (new Foo(1, 2)) { case Foo(int z) -> z; }");
    }

    @org.testng.annotations.BeforeMethod
    public void setUp() {
        super.setUp(bc -> bc.compilerOptions("--source", System.getProperty("java.specification.version"), "--enable-preview").remoteVMOptions("--enable-preview"));
    }
}
