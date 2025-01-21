/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8189193
 * @library /test/lib
 * @build jdk.test.lib.process.ProcessTools
 * @build java.base/java.lang.BootNativeLibrary BootLoaderTest FindClassFromBoot
 * @run main/othervm/native -Xcheck:jni FindClassFromBoot
 * @summary verify if the native library loaded by the boot loader
 *          can only find classes visible to the boot loader
 */

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.test.lib.process.ProcessTools;

public class FindClassFromBoot {
    public static void main(String... args) throws Exception {
        Path patches = Paths.get(System.getProperty("test.classes"), "patches", "java.base");
        String syspaths = System.getProperty("sun.boot.library.path") +
                              File.pathSeparator + System.getProperty("java.library.path");
        ProcessTools.executeTestJava("-Dsun.boot.library.path=" + syspaths,
                                    "--patch-module", "java.base=" + patches.toString(),
                                    "BootLoaderTest")
                    .shouldHaveExitValue(0);
    }
}
