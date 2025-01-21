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
 * @run driver ClassListFormatC
 */

public class ClassListFormatC extends ClassListFormatBase {
    static {
        // Uncomment the following line to run only one of the test cases
        // ClassListFormatBase.RUN_ONLY_TEST = "TESTCASE C1";
    }

    public static void main(String[] args) throws Throwable {
        String appJar = JarBuilder.getOrCreateHelloJar();
        String customJarPath = JarBuilder.build("ClassListFormatC", "CustomLoadee",
                                                "CustomLoadee2", "CustomInterface2_ia",
                                                "CustomInterface2_ib");

        //----------------------------------------------------------------------
        // TESTGROUP C: if source IS NOT specified
        //----------------------------------------------------------------------
        dumpShouldFail(
            "TESTCASE C1: if source: is NOT specified, must NOT specify super:",
            appJar, classlist(
                "Hello",
                "java/lang/Object id: 1",
                "CustomLoadee super: 1"
            ),
            "If source location is not specified, super class must not be specified");

        dumpShouldFail(
            "TESTCASE C2: if source: is NOT specified, must NOT specify interface:",
            appJar, classlist(
                "Hello",
                "java/lang/Object id: 1",
                "CustomLoadee interfaces: 1"
            ),
            "If source location is not specified, interface(s) must not be specified");
    }
}
