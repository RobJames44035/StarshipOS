/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @summary AppCDS handling of protection domain in custom loaders.
 *
 * @requires vm.cds
 * @requires vm.cds.custom.loaders
 *
 * @library /test/lib /test/hotspot/jtreg/runtime/cds/appcds
 * @compile test-classes/ProtDomain.java
 * @run driver ProtectionDomain
 */

public class ProtectionDomain {
    public static void main(String[] args) throws Exception {
        String appJar = JarBuilder.build("ProtectionDomain-app", "ProtDomain");

        String customJar = JarBuilder.build("ProtectionDomain-custom",
            "ProtDomainClassForArchive", "ProtDomainNotForArchive");
        String[] classlist = new String[] {
            "java/lang/Object id: 1",
            "ProtDomain id: 2 super: 1 source: " + appJar,
            "ProtDomainClassForArchive id: 3 super: 1 source: " + customJar
        };

        TestCommon.testDump(appJar, classlist);

        // First class is loaded from CDS, second class is loaded from JAR
        TestCommon.checkExec(TestCommon.exec(appJar, "-verbose:class", "ProtDomain", customJar),
            "[class,load] ProtDomainClassForArchive source: shared objects file",
            "[class,load] ProtDomainNotForArchive source: file");
    }
}
