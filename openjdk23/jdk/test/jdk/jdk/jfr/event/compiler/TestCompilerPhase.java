/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.event.compiler;

import java.lang.reflect.Method;
import java.util.List;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.Utils;
import jdk.test.lib.jfr.EventNames;
import jdk.test.lib.jfr.Events;
import jdk.test.whitebox.WhiteBox;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @requires vm.compMode!="Xint" & vm.flavor == "server" & (vm.opt.TieredStopAtLevel == 4 | vm.opt.TieredStopAtLevel == null)
 * @library /test/lib
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:.
 *     -XX:-NeverActAsServerClassMachine
 *     -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI
 *     -XX:CompileOnly=jdk.jfr.event.compiler.TestCompilerPhase::dummyMethod
 *     -XX:+SegmentedCodeCache -Xbootclasspath/a:.
 *     jdk.jfr.event.compiler.TestCompilerPhase
 */
public class TestCompilerPhase {
    private final static String EVENT_NAME = EventNames.CompilerPhase;
    private final static String METHOD_NAME = "dummyMethod";
    private static final int COMP_LEVEL_SIMPLE = 1;
    private static final int COMP_LEVEL_FULL_OPTIMIZATION = 4;

    public static void main(String[] args) throws Exception {
        Recording recording = new Recording();
        recording.enable(EVENT_NAME);
        recording.start();

        // Provoke compilation
        Method mtd = TestCompilerPhase.class.getDeclaredMethod(METHOD_NAME, new Class[0]);
        WhiteBox WB = WhiteBox.getWhiteBox();
        String directive = "[{ match: \"" + TestCompilerPhase.class.getName().replace('.', '/')
                + "." + METHOD_NAME + "\", " + "BackgroundCompilation: false }]";
        WB.addCompilerDirective(directive);
        if (!WB.enqueueMethodForCompilation(mtd, COMP_LEVEL_FULL_OPTIMIZATION)) {
            WB.enqueueMethodForCompilation(mtd, COMP_LEVEL_SIMPLE);
        }
        Utils.waitForCondition(() -> WB.isMethodCompiled(mtd));
        dummyMethod();

        recording.stop();

        List<RecordedEvent> events = Events.fromRecording(recording);
        Events.hasEvents(events);
        for (RecordedEvent event : events) {
            System.out.println("Event:" + event);
            Events.assertField(event, "phase").notEmpty();
            Events.assertField(event, "compileId").atLeast(0);
            Events.assertField(event, "phaseLevel").atLeast((short)0).atMost((short)5);
            Events.assertEventThread(event);
        }
    }

    static void dummyMethod() {
        System.out.println("hello!");
    }
}
