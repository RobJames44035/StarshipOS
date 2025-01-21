/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/**
 * @test
 * @requires vm.hasJFR & vm.cds
 * @library /test/lib /test/hotspot/jtreg/runtime/cds/appcds
 * @run driver/timeout=480 ModulePathAndCP_JFR
 * @summary Same as ModulePathAndCP, but add -XX:StartFlightRecording:dumponexit=true to the runtime
 *          options. This makes sure that the shared classes are compatible with both
 *          JFR and JVMTI ClassFileLoadHook.
 */

public class ModulePathAndCP_JFR {
    public static void main(String... args) throws Exception {
        ModulePathAndCP.run("-XX:StartFlightRecording:dumponexit=true", "-Xlog:cds+jvmti=debug,jfr+startup=off");
    }
}

