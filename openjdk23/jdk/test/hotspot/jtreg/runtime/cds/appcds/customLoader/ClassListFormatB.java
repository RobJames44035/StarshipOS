/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @summary Tests the format checking of hotspot/src/closed/share/vm/classfile/classListParser.cpp.
 *
 * @requires vm.cds
 * @requires vm.cds.custom.loaders
 * @library /test/lib /test/hotspot/jtreg/runtime/cds/appcds
 * @compile ../test-classes/Hello.java test-classes/CustomLoadee.java test-classes/CustomLoadee2.java
 *          test-classes/CustomInterface2_ia.java test-classes/CustomInterface2_ib.java
 * @run driver ClassListFormatB
 */

public class ClassListFormatB extends ClassListFormatBase {
    static {
        // Uncomment the following line to run only one of the test cases
        // ClassListFormatBase.RUN_ONLY_TEST = "TESTCASE B1";
    }

    public static void main(String[] args) throws Throwable {
        String appJar = JarBuilder.getOrCreateHelloJar();
        String customJarPath = JarBuilder.build("ClassListFormatB", "CustomLoadee",
                                            "CustomLoadee2", "CustomInterface2_ia", "CustomInterface2_ib");
        //----------------------------------------------------------------------
        // TESTGROUP B if source IS specified
        //----------------------------------------------------------------------
        dumpShouldFail(
            "TESTCASE B1: if source: is specified, must specify super:",
            appJar, classlist(
                "Hello",
                "java/lang/Object id: 1",
                "CustomLoadee id: 2 source: " + customJarPath
            ),
            "If source location is specified, super class must be also specified");

        dumpShouldFail(
            "TESTCASE B2: if source: is specified, must specify id:",
            appJar, classlist(
                "Hello",
                "java/lang/Object id: 1",
                "CustomLoadee super: 1 source: " + customJarPath
            ),
            "If source location is specified, id must be also specified");
    }
}
