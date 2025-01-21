/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @summary Ensure that support for AppCDS custom class loaders are not enabled on unsupported platforms.
 * @requires vm.cds
 * @library /test/lib /test/hotspot/jtreg/runtime/cds/appcds
 * @compile test-classes/SimpleHello.java
 * @run driver UnsupportedPlatforms
 */

import jdk.test.lib.Platform;
import jdk.test.lib.process.OutputAnalyzer;

public class UnsupportedPlatforms {
    public static String PLATFORM_NOT_SUPPORTED_WARNING =
        "AppCDS custom class loaders not supported on this platform";

    public static void main(String[] args) throws Exception {
        String appJar = JarBuilder.build("UnsupportedPlatforms", "SimpleHello");

        // Dump the archive
        String classlist[] = new String[] {
            "SimpleHello",
            "java/lang/Object id: 1",
            "CustomLoadee id: 2 super: 1 source: " + appJar
        };

        OutputAnalyzer out = TestCommon.dump(appJar, classlist);

        if (Platform.areCustomLoadersSupportedForCDS()) {
            out.shouldNotContain(PLATFORM_NOT_SUPPORTED_WARNING);
            out.shouldHaveExitValue(0);
        } else {
            out.shouldContain(PLATFORM_NOT_SUPPORTED_WARNING);
            out.shouldHaveExitValue(1);
        }
    }
}
