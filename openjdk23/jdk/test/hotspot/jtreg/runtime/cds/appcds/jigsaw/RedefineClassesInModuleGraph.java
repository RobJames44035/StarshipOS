/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8249276
 * @summary Make sure that archived module graph is not loaded if critical classes have been redefined.
 * @library /test/lib /test/hotspot/jtreg/runtime/cds/appcds
 * @requires vm.cds
 * @requires vm.flavor != "minimal"
 * @modules java.instrument
 * @run driver RedefineClassesInModuleGraph
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.helpers.ClassFileInstaller;

public class RedefineClassesInModuleGraph {
    public static String appClasses[] = {
        RedefineClassesInModuleGraphApp.class.getName(),
    };
    public static String agentClasses[] = {
        RedefineClassesInModuleGraphAgent.class.getName(),
        RedefineClassesInModuleGraphTransformer.class.getName(),
    };

    private static final String MANIFEST =
        "Manifest-Version: 1.0\n" +
        "Premain-Class: RedefineClassesInModuleGraphAgent\n" +
        "Can-Retransform-Classes: true\n" +
        "Can-Redefine-Classes: true\n";

    public static void main(String[] args) throws Throwable {
        String agentJar =
            ClassFileInstaller.writeJar("RedefineClassesInModuleGraphAgent.jar",
                                        ClassFileInstaller.Manifest.fromString(MANIFEST),
                                        agentClasses);

        String appJar =
            ClassFileInstaller.writeJar("RedefineClassesInModuleGraphApp.jar", appClasses);

        TestCommon.testDump(appJar, agentClasses);

        TestCommon.run(
            "-cp", appJar,
            "-javaagent:" + agentJar,
            RedefineClassesInModuleGraphApp.class.getName())
          .assertNormalExit();
    }
}

