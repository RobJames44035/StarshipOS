/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8146416
 * @library /test/lib /
 *
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *      -XX:+WhiteBoxAPI -Xbatch -Xmx100m
 *      -XX:CompileCommand=exclude,compiler.uncommontrap.DeoptReallocFailure::main
 *      compiler.uncommontrap.DeoptReallocFailure
 *
 */

package compiler.uncommontrap;

import jdk.test.whitebox.WhiteBox;

import java.lang.reflect.Method;

public class DeoptReallocFailure {
    static class MemoryChunk {
        MemoryChunk other;
        Object[][] array;

        MemoryChunk(MemoryChunk other) {
            this.other = other;
            array = new Object[1024 * 256][];
        }
    }

    static class NoEscape {
        long f1;
    }

    static MemoryChunk root;
    private static final WhiteBox WB = WhiteBox.getWhiteBox();

    public static synchronized long  test() {
        NoEscape[] noEscape = new NoEscape[45];
        noEscape[0] = new NoEscape();
        for (int i=0;i<1024*256;i++) {
           root.array[i]= new Object[4500];
        }
        return noEscape[0].f1;
    }

    public static void main(String[] args) throws Throwable {

        //Exhaust Memory
        root = null;
        try {
            while (true) {
                root = new MemoryChunk(root);
            }
        } catch (OutOfMemoryError oom) {
        }

        if (root == null) {
          return;
        }

        try {
            NoEscape dummy = new NoEscape();
            Method m = DeoptReallocFailure.class.getMethod("test");
            WB.enqueueMethodForCompilation(m, 4);
            test();
        } catch (OutOfMemoryError oom) {
            root = null;
            oom.printStackTrace();
        }
        System.out.println("TEST PASSED");
    }
}
