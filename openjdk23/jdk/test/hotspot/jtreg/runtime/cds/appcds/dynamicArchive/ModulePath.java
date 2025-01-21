/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.test.lib.cds.CDSTestUtils;

/*
 * @test
 * @summary Dyanmic archive with module path
 * @requires vm.cds
 * @library /test/lib /test/hotspot/jtreg/runtime/cds/appcds /test/hotspot/jtreg/runtime/cds/appcds/test-classes
 * @compile ../test-classes/Hello.java
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller -jar WhiteBox.jar jdk.test.whitebox.WhiteBox
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -Xbootclasspath/a:./WhiteBox.jar ModulePath
 */

public class ModulePath extends DynamicArchiveTestBase {
    private static final Path USER_DIR = Paths.get(CDSTestUtils.getOutputDir());

    private static final String FS = File.separator;
    private static final String TEST_SRC = System.getProperty("test.src") +
        FS + ".." + FS + "jigsaw" + FS + "modulepath";

    private static final Path SRC_DIR = Paths.get(TEST_SRC, "src");
    private static final Path MODS_DIR = Paths.get("mods");

    // the module name of the test module
    private static final String TEST_MODULE = "com.simple";

    // the module main class
    private static final String MAIN_CLASS = "com.simple.Main";

    private static Path moduleDir = null;
    private static Path srcJar = null;

    public static void buildTestModule() throws Exception {

        // javac -d mods/$TESTMODULE --module-path MOD_DIR src/$TESTMODULE/**
        JarBuilder.compileModule(SRC_DIR.resolve(TEST_MODULE),
                                 MODS_DIR.resolve(TEST_MODULE),
                                 MODS_DIR.toString());


        moduleDir = Files.createTempDirectory(USER_DIR, "mlib");
        srcJar = moduleDir.resolve(TEST_MODULE + ".jar");
        String classes = MODS_DIR.resolve(TEST_MODULE).toString();
        JarBuilder.createModularJar(srcJar.toString(), classes, MAIN_CLASS);
    }

    public static void main(String[] args) throws Exception {
        runTest(ModulePath::test);
    }

    static void test(String args[]) throws Exception {
        String topArchiveName = getNewArchiveName("top");
        String baseArchiveName = getNewArchiveName("base");

        String appJar    = JarBuilder.getOrCreateHelloJar();
        String mainClass = "Hello";

        // create a base archive with the --module-path option
        buildTestModule();
        baseArchiveName = getNewArchiveName("base-with-module");
        String appClasses[] = {mainClass};
        TestCommon.dumpBaseArchive(baseArchiveName,
                        appClasses,
                        "-Xlog:class+load",
                        "-cp", appJar,
                        "--module-path", moduleDir.toString(),
                        "-m", TEST_MODULE);

        // Dumping of dynamic archive should be successful if the specified
        // --module-path is the same as for the base archive.
        topArchiveName = getNewArchiveName("top-with-module");
        dump2(baseArchiveName, topArchiveName,
              "-Xlog:cds*",
              "-Xlog:cds+dynamic=debug",
              "-Xlog:class+path=info,class+load",
              "-cp", appJar,
              "--module-path", moduleDir.toString(),
              "-m", TEST_MODULE, MAIN_CLASS)
            .assertNormalExit();

        // Load the Hello class from the base archive.
        run2(baseArchiveName, topArchiveName,
            "-Xlog:class+load",
            "-Xlog:cds+dynamic=debug,cds=debug",
            "-cp", appJar, mainClass)
            .assertNormalExit(output -> {
                    output.shouldContain("Hello source: shared objects file")
                          .shouldHaveExitValue(0);
                });

        // Load the com.simple.Main class from the dynamic archive.
        run2(baseArchiveName, topArchiveName,
            "-Xlog:class+load",
            "-Xlog:cds+dynamic=debug,cds=debug",
            "-cp", appJar,
            "--module-path", moduleDir.toString(),
            "-m", TEST_MODULE, MAIN_CLASS)
            .assertNormalExit(output -> {
                    output.shouldContain("com.simple.Main source: shared objects file (top)")
                          .shouldHaveExitValue(0);
                });
    }
}
