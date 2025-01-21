/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8343416
 * @summary Test dumping of class from a system module loaded by a custom loader.
 * @requires vm.cds
 * @requires vm.cds.custom.loaders
 * @library /test/lib /test/hotspot/jtreg/runtime/cds/appcds
 * @run driver ClassFromSystemModule
 */

import java.nio.file.*;

import jdk.test.lib.process.OutputAnalyzer;

public class ClassFromSystemModule {
    public static void main(String[] args) throws Exception {
        Path jrtFs = Paths.get(System.getProperty("java.home"), "lib", "jrt-fs.jar");
        System.out.println("jrtFs: " + jrtFs.toString());

        String classlist[] = new String[] {
            "java/nio/file/spi/FileSystemProvider id: 1000",
            "jdk/internal/jrtfs/JrtFileSystemProvider id: 1001 super:1000 source: " + jrtFs.toString(),
        };

        OutputAnalyzer out = TestCommon.testDump(null, classlist, "-Xlog:cds,cds+class=debug");
        out.shouldContain("boot  java.nio.file.spi.FileSystemProvider")
           .shouldContain("unreg jdk.internal.jrtfs.JrtFileSystemProvider");
    }
}
