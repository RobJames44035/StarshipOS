/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/**
 * @test
 * @bug 8308903
 * @summary Test the contents of -Xlog:cds+map
 * @requires vm.cds
 * @library /test/lib
 * @run driver CDSMapTest
 */

import jdk.test.lib.cds.CDSOptions;
import jdk.test.lib.cds.CDSTestUtils;
import jdk.test.lib.Platform;
import java.util.ArrayList;

public class CDSMapTest {
    public static void main(String[] args) throws Exception {
        doTest(false);

        if (Platform.is64bit()) {
            // There's no oop/klass compression on 32-bit.
            doTest(true);
        }
    }

    public static void doTest(boolean compressed) throws Exception {
        ArrayList<String> dumpArgs = new ArrayList<>();

        // Use the same heap size as make/Images.gmk
        dumpArgs.add("-Xmx128M");

        if (Platform.is64bit()) {
            // These options are available only on 64-bit.
            String sign = (compressed) ?  "+" : "-";
            dumpArgs.add("-XX:" + sign + "UseCompressedOops");
        }

        dump(dumpArgs);
    }

    static int id = 0;
    static void dump(ArrayList<String> args, String... more) throws Exception {
        String logName = "SharedArchiveFile" + (id++);
        String archiveName = logName + ".jsa";
        String mapName = logName + ".map";
        CDSOptions opts = (new CDSOptions())
            .addPrefix("-Xlog:cds=debug")
            .addPrefix("-Xlog:cds+map=debug,cds+map+oops=trace:file=" + mapName + ":none:filesize=0")
            .setArchiveName(archiveName)
            .addSuffix(args)
            .addSuffix(more);
        CDSTestUtils.createArchiveAndCheck(opts);

        CDSMapReader.MapFile mapFile = CDSMapReader.read(mapName);
        CDSMapReader.validate(mapFile);
    }
}
