/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/**
 * @test
 * @library /test/lib
 * @build m/*
 * @run main/othervm --add-modules m test.GetPackagesTest
 * @summary test the packages returned by Module::getPackages for an unnamed
 *          module does not include the packages for named modules
 */

package test;

import java.util.Set;
import static jdk.test.lib.Asserts.*;

public class GetPackagesTest {
    public static void main(String... args) throws Exception {
        // module m contains the package "p"
        Class<?> c = Class.forName("p.Main");
        Module m = c.getModule();
        Module test = GetPackagesTest.class.getModule();

        // module m and unnamed module are defined by the same class loader
        assertTrue(m.isNamed());
        assertFalse(test.isNamed());
        assertTrue(m.getClassLoader() == test.getClassLoader());

        // verify Module::getPackages on an unnamed module only contains
        // the packages defined to the unnamed module
        assertEquals(m.getPackages(), Set.of("p"));

        Set<String> pkgs = test.getPackages();
        assertTrue(pkgs.contains("test"));
        assertFalse(pkgs.contains("p"));
    }
}
