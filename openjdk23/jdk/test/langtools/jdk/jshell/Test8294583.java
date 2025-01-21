/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8294583
 * @summary JShell: NPE in switch with non existing record pattern
 * @build KullaTesting TestingInputStream
 * @run testng Test8294583
 */

import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

@Test
public class Test8294583 extends KullaTesting {

    public void test() {
        assertEvalFail("switch (new Object()) {\n" +
                        "   case Foo() -> {}\n" +
                        "};");
    }

    @org.testng.annotations.BeforeMethod
    public void setUp() {
        super.setUp(bc -> bc.compilerOptions("--source", System.getProperty("java.specification.version"), "--enable-preview").remoteVMOptions("--enable-preview"));
    }
}
