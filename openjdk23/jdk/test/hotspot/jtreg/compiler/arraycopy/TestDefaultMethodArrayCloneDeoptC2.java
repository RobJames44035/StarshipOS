/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8170455
 * @summary C2: Access to [].clone from interfaces fails.
 * @library /test/lib /
 *
 * @requires vm.flavor == "server" & !vm.emulatedClient
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xcomp -Xbatch -Xbootclasspath/a:.  -XX:-TieredCompilation  -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI
 *                   -XX:CompileCommand=compileonly,*TestDefaultMethodArrayCloneDeoptC2Interface::test
 *                   compiler.arraycopy.TestDefaultMethodArrayCloneDeoptC2
 */

package compiler.arraycopy;

import jdk.test.whitebox.WhiteBox;
import java.lang.reflect.Method;
import compiler.whitebox.CompilerWhiteBoxTest;



interface TestDefaultMethodArrayCloneDeoptC2Interface {
    default int[] test(int[] arr) {
        return arr.clone();
    }

    default TDMACDC2InterfaceTypeTest[] test(TDMACDC2InterfaceTypeTest[] arr) {
        return arr.clone();
    }

    default TDMACDC2ClassTypeTest[] test(TDMACDC2ClassTypeTest[] arr) {
        return arr.clone();
    }
}

public class TestDefaultMethodArrayCloneDeoptC2 implements TestDefaultMethodArrayCloneDeoptC2Interface {
    private static final WhiteBox WB = WhiteBox.getWhiteBox();
    public static TestDefaultMethodArrayCloneDeoptC2 a = new TestDefaultMethodArrayCloneDeoptC2();

    public static void main(String[] args) throws Exception {
        testPrimitiveArr();
        testIntfArr();
        testClassArr();
    }

    public static void testPrimitiveArr() throws Exception {
        Method m = TestDefaultMethodArrayCloneDeoptC2Interface.class.getMethod("test", int[].class);
        a.test(new int[1]); // Compiled
        a.test(new int[1]);
        if (!WB.isMethodCompiled(m)) {
            WB.enqueueMethodForCompilation(m, CompilerWhiteBoxTest.COMP_LEVEL_FULL_OPTIMIZATION);
        }
        a.test(new int[1]);
        if (!WB.isMethodCompiled(m)) {
            throw new Exception("Method should be compiled");
        }
    }

    public static void testIntfArr() throws Exception {
        Method m = TestDefaultMethodArrayCloneDeoptC2Interface.class.getMethod("test", TDMACDC2InterfaceTypeTest[].class);
        a.test(new TDMACDC2InterfaceTypeTest[1]); // Compiled, Decompile unloaded
        a.test(new TDMACDC2InterfaceTypeTest[1]); // Compiled
        a.test(new TDMACDC2InterfaceTypeTest[1]);
        if (!WB.isMethodCompiled(m)) {
            WB.enqueueMethodForCompilation(m, CompilerWhiteBoxTest.COMP_LEVEL_FULL_OPTIMIZATION);
        }
        a.test(new TDMACDC2InterfaceTypeTest[1]);
        if (!WB.isMethodCompiled(m)) {
            throw new Exception("Method should be compiled");
        }
    }

    public static void testClassArr() throws Exception {
        Method m = TestDefaultMethodArrayCloneDeoptC2Interface.class.getMethod("test", TDMACDC2ClassTypeTest[].class);
        a.test(new TDMACDC2ClassTypeTest[1]); // Compiled, Decompile unloaded
        a.test(new TDMACDC2ClassTypeTest[1]); // Compiled
        a.test(new TDMACDC2ClassTypeTest[1]);
        if (!WB.isMethodCompiled(m)) {
            WB.enqueueMethodForCompilation(m, CompilerWhiteBoxTest.COMP_LEVEL_FULL_OPTIMIZATION);
        }
        a.test(new TDMACDC2ClassTypeTest[1]);
        if (!WB.isMethodCompiled(m)) {
            throw new Exception("Method should be compiled");
        }
    }
}

interface TDMACDC2InterfaceTypeTest {
}

class TDMACDC2ClassTypeTest {
}

