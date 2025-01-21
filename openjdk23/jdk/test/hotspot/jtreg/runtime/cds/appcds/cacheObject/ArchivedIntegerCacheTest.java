/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @summary Test primitive box caches integrity in various scenarios (IntegerCache etc)
 * @requires vm.cds.write.archived.java.heap
 * @library /test/jdk/lib/testlibrary /test/lib /test/hotspot/jtreg/runtime/cds/appcds
 * @compile --add-exports java.base/jdk.internal.misc=ALL-UNNAMED CheckIntegerCacheApp.java ArchivedIntegerHolder.java
 * @run driver jdk.test.lib.helpers.ClassFileInstaller -jar boxCache.jar CheckIntegerCacheApp
 * @run driver jdk.test.lib.helpers.ClassFileInstaller -jar boxCache-boot.jar ArchivedIntegerHolder
 * @run driver ArchivedIntegerCacheTest
 */

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.test.lib.cds.CDSTestUtils;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.helpers.ClassFileInstaller;

public class ArchivedIntegerCacheTest {

    public static String[] mixArgs(String... args) {
        String bootJar = ClassFileInstaller.getJarPath("boxCache-boot.jar");

        String[] newArgs = new String[args.length + 5];
        newArgs[0] = "--add-exports";
        newArgs[1] = "java.base/jdk.internal.misc=ALL-UNNAMED";
        newArgs[2] = "-Xbootclasspath/a:" + bootJar;
        newArgs[3] = "-XX:+IgnoreUnrecognizedVMOptions";
        newArgs[4] = "-XX:ArchiveHeapTestClass=ArchivedIntegerHolder";
        System.arraycopy(args, 0, newArgs, 5, args.length);
        return newArgs;
    }

    public static void main(String[] args) throws Exception {
        String appJar = ClassFileInstaller.getJarPath("boxCache.jar");

        Path userDir = Paths.get(CDSTestUtils.getOutputDir());
        Path moduleDir = Files.createTempDirectory(userDir, "mods");

        //
        // Dump default archive
        //
        OutputAnalyzer output = TestCommon.dump(appJar,
                TestCommon.list("CheckIntegerCacheApp"),
                mixArgs());
        TestCommon.checkDump(output);

        // Test case 1)
        // - Default options
        System.out.println("----------------------- Test case 1 ----------------------");
        output = TestCommon.exec(appJar,
                mixArgs("CheckIntegerCacheApp", "127"));
        TestCommon.checkExec(output);

        // Test case 2)
        // - Default archive
        // - Larger -XX:AutoBoxCacheMax
        System.out.println("----------------------- Test case 2 ----------------------");
        output = TestCommon.exec(appJar,
                mixArgs("-XX:AutoBoxCacheMax=20000",
                        "CheckIntegerCacheApp", "20000"));
        TestCommon.checkExec(output);

        //
        // Dump with -XX:AutoBoxCacheMax specified
        //
        output = TestCommon.dump(appJar,
                TestCommon.list("CheckIntegerCacheApp"),
                mixArgs("-XX:AutoBoxCacheMax=20000"));
        TestCommon.checkDump(output);

        // Test case 3)
        // - Large archived cache
        // - Default options
        System.out.println("----------------------- Test case 3 ----------------------");
        output = TestCommon.exec(appJar,
                mixArgs("--module-path", moduleDir.toString(),
                        "CheckIntegerCacheApp", "127"));
        TestCommon.checkExec(output);


        // Test case 4)
        // - Large archived cache
        // - Matching options
        System.out.println("----------------------- Test case 4 ----------------------");
        output = TestCommon.exec(appJar,
                mixArgs("--module-path", moduleDir.toString(),
                        "-XX:AutoBoxCacheMax=20000",
                        "CheckIntegerCacheApp", "20000"));
        TestCommon.checkExec(output);

        // Test case 5)
        // - Large archived cache
        // - Larger requested cache
        System.out.println("----------------------- Test case 5 ----------------------");
        output = TestCommon.exec(appJar,
                mixArgs("--module-path", moduleDir.toString(),
                        "-XX:AutoBoxCacheMax=30000",
                        "CheckIntegerCacheApp", "30000"));
        TestCommon.checkExec(output);

        // Test case 6)
        // - Cache is too large to archive
        output = TestCommon.dump(appJar,
                TestCommon.list("CheckIntegerCacheApp"),
                mixArgs("-XX:AutoBoxCacheMax=2000000",
                        "-Xmx1g",
                        "-XX:NewSize=1g",
                        "-Xlog:cds+heap=info",
                        "-Xlog:gc+region+cds",
                        "-Xlog:gc+region=trace"));
        TestCommon.checkDump(output,
            "Cannot archive the sub-graph referenced from [Ljava.lang.Integer; object");
    }
}
