/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @summary Fieldref entry for putfield bytecodes for a final field cannot be preresolved if it's used by a
 *          method outside of <clinit>
 * @requires vm.cds
 * @requires vm.compMode != "Xcomp"
 * @library /test/lib
 * @build ResolvedPutFieldHelper
 * @build ResolvedPutField
 * @run driver jdk.test.lib.helpers.ClassFileInstaller -jar app.jar ResolvedPutFieldApp ResolvedPutFieldHelper
 * @run driver ResolvedPutField
 */

import jdk.test.lib.cds.CDSOptions;
import jdk.test.lib.cds.CDSTestUtils;
import jdk.test.lib.helpers.ClassFileInstaller;

public class ResolvedPutField {
    static final String classList = "ResolvedPutField.classlist";
    static final String appJar = ClassFileInstaller.getJarPath("app.jar");
    static final String mainClass = ResolvedPutFieldApp.class.getName();
    static final String error = "Update to non-static final field ResolvedPutFieldHelper.x attempted from a different method (set_x) than the initializer method <init>";
    public static void main(String[] args) throws Exception {
        // dump class list
        CDSTestUtils.dumpClassList(classList, "-cp", appJar, mainClass)
            .assertNormalExit(error);

        CDSOptions opts = (new CDSOptions())
            .addPrefix("-XX:ExtraSharedClassListFile=" + classList,
                       "-cp", appJar,
                       "-Xlog:cds+resolve=trace");
        CDSTestUtils.createArchiveAndCheck(opts)
            .shouldMatch("cds,resolve.*Failed to resolve putfield .*ResolvedPutFieldHelper -> ResolvedPutFieldHelper.x:I");
    }
}

class ResolvedPutFieldApp {
    public static void main(String args[]) {
        try {
            ResolvedPutFieldHelper.main(args);
        } catch (IllegalAccessError e) {
            System.out.println("IllegalAccessError expected:");
            System.out.println(e);
            System.exit(0);
        }
        throw new RuntimeException("IllegalAccessError expected!");
    }
}
