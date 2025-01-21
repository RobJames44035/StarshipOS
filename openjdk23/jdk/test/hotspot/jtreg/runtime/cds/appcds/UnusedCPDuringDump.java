/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8209385
 * @summary non-empty dir in -cp should be fine during dump time if only classes
 *          from the system modules are being loaded even though some are
 *          defined to the PlatformClassLoader and AppClassLoader.
 * @requires vm.cds
 * @library /test/lib
 * @compile test-classes/Hello.java
 * @run main/othervm -Dtest.cds.copy.child.stdout=false UnusedCPDuringDump
 */

import java.io.File;
import jdk.test.lib.cds.CDSTestUtils;
import jdk.test.lib.process.OutputAnalyzer;

public class UnusedCPDuringDump {

    public static void main(String[] args) throws Exception {
        File dir = CDSTestUtils.getOutputDirAsFile();
        File emptydir = new File(dir, "emptydir");
        emptydir.mkdir();
        String appJar = JarBuilder.getOrCreateHelloJar();

        OutputAnalyzer output = TestCommon.dump(dir.getPath(),
            TestCommon.list("sun/util/resources/cldr/provider/CLDRLocaleDataMetaInfo",
                            "com/sun/tools/javac/main/Main"),
                            "-Xlog:class+path=info,class+load=debug");
        TestCommon.checkDump(output,
                             "[class,load] sun.util.resources.cldr.provider.CLDRLocaleDataMetaInfo",
                             "for instance a 'jdk/internal/loader/ClassLoaders$PlatformClassLoader'",
                             "[class,load] com.sun.tools.javac.main.Main",
                             "for instance a 'jdk/internal/loader/ClassLoaders$AppClassLoader'");

        String jsaOpt = "-XX:SharedArchiveFile=" + TestCommon.getCurrentArchiveName();
        TestCommon.run("-cp", appJar, jsaOpt, "-Xlog:class+path=info,class+load=debug", "Hello")
            .assertNormalExit("Hello World");
  }
}
