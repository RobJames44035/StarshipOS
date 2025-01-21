/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/**
 * @test
 * @summary Make sure CDS and JFR work together.
 * @requires vm.hasJFR & vm.cds
 * @library /test/lib /test/hotspot/jtreg/runtime/cds/appcds /test/hotspot/jtreg/runtime/cds/appcds/test-classes test-classes
 * @build Hello GetFlightRecorder
 * @run driver jdk.test.lib.helpers.ClassFileInstaller -jar CDSandJFR.jar Hello GetFlightRecorder GetFlightRecorder$TestEvent GetFlightRecorder$SimpleEvent
 * @run main/othervm/timeout=500 CDSandJFR
 */

import jdk.test.lib.BuildHelper;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.helpers.ClassFileInstaller;

public class CDSandJFR {
    static String[] classes = {
        "jdk/jfr/Event",
        "jdk/jfr/events/FileReadEvent",
        "jdk/jfr/events/FileWriteEvent",
        "jdk/jfr/events/SocketReadEvent",
        "jdk/jfr/events/SocketWriteEvent",
        "jdk/jfr/events/ExceptionThrownEvent",
        "jdk/jfr/events/ExceptionStatisticsEvent",
        "jdk/jfr/events/ErrorThrownEvent",
        "jdk/jfr/events/ActiveSettingEvent",
        "jdk/jfr/events/ActiveRecordingEvent",
        "Hello",
        "GetFlightRecorder",
        "GetFlightRecorder$TestEvent",
    };

    public static void main(String[] args) throws Exception {
        test(classes);
    }

    static void test(String[] classes) throws Exception {
        String appJar = ClassFileInstaller.getJarPath("CDSandJFR.jar");
        OutputAnalyzer output;
        output = TestCommon.testDump(appJar, TestCommon.list(classes));
        TestCommon.checkDump(output, "Skipping jdk/jfr/Event: JFR event class");

        output = TestCommon.exec(appJar,
                                 "-XX:StartFlightRecording:dumponexit=true",
                                 "Hello");
        TestCommon.checkExec(output, "Hello World");

        TestCommon.checkExec(TestCommon.exec(appJar,
                                             "-XX:FlightRecorderOptions:retransform=true",
                                             "GetFlightRecorder"));
        TestCommon.checkExec(TestCommon.exec(appJar,
                                             "-XX:FlightRecorderOptions:retransform=false",
                                             "GetFlightRecorder"));

        // Test dumping with flight recorder enabled.
        output = TestCommon.testDump(appJar, TestCommon.list(classes),
                                     "-XX:StartFlightRecording:dumponexit=true");
        TestCommon.checkDump(output, "warning: JFR will be disabled during CDS dumping");
    }
}
