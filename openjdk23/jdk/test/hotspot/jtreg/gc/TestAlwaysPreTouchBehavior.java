/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package gc;

/**
 * @test id=ParallelCollector
 * @summary tests AlwaysPreTouch
 * @requires vm.gc.Parallel
 * @requires os.maxMemory > 2G
 * @requires os.family != "aix"
 * @library /test/lib
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -Xmx512m -Xms512m -XX:+UseParallelGC -XX:+AlwaysPreTouch gc.TestAlwaysPreTouchBehavior
 */

 /**
 * @test id=SerialCollector
 * @summary tests AlwaysPreTouch
 * @requires vm.gc.Serial
 * @requires os.maxMemory > 2G
 * @requires os.family != "aix"
 * @library /test/lib
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -Xmx512m -Xms512m -XX:+UseSerialGC -XX:+AlwaysPreTouch gc.TestAlwaysPreTouchBehavior
 */

/**
 * @test id=Shenandoah
 * @summary tests AlwaysPreTouch
 * @requires vm.gc.Shenandoah
 * @requires os.maxMemory > 2G
 * @requires os.family != "aix"
 * @library /test/lib
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -Xmx512m -Xms512m -XX:+UseShenandoahGC  -XX:+AlwaysPreTouch gc.TestAlwaysPreTouchBehavior
 */

/**
 * @test id=G1
 * @summary tests AlwaysPreTouch
 * @requires vm.gc.G1
 * @requires os.maxMemory > 2G
 * @requires os.family != "aix"
 * @library /test/lib
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -Xmx512m -Xms512m -XX:+AlwaysPreTouch gc.TestAlwaysPreTouchBehavior
 */

/**
 * @test id=Z
 * @summary tests AlwaysPreTouch
 * @requires vm.gc.Z
 * @requires os.maxMemory > 2G
 * @requires os.family != "aix"
 * @library /test/lib
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -XX:+UseZGC -Xmx512m -Xms512m -XX:+AlwaysPreTouch gc.TestAlwaysPreTouchBehavior
 */

/**
 * @test id=Epsilon
 * @summary tests AlwaysPreTouch
 * @requires vm.gc.Epsilon
 * @requires os.maxMemory > 2G
 * @requires os.family != "aix"
 * @library /test/lib
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC -Xmx512m -Xms512m -XX:+AlwaysPreTouch gc.TestAlwaysPreTouchBehavior
 */


import jdk.test.lib.Asserts;

import jdk.test.whitebox.WhiteBox;

public class TestAlwaysPreTouchBehavior {

    public static void main(String [] args) {
    long rss = WhiteBox.getWhiteBox().rss();
    System.out.println("RSS: " + rss);
    if (rss == 0) {
        System.out.println("cannot get RSS, just skip");
        return; // Did not get available RSS, just ignore this test.
    }
    Runtime runtime = Runtime.getRuntime();
    long committedMemory = runtime.totalMemory();
    Asserts.assertGreaterThan(rss, committedMemory, "RSS of this process(" + rss + "b) should be bigger than or equal to committed heap mem(" + committedMemory + "b)");
   }
}

