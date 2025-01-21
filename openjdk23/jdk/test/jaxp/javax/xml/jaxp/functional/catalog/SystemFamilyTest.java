/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package catalog;

import static catalog.CatalogTestUtils.catalogResolver;
import static catalog.ResolutionChecker.checkNoMatch;
import static catalog.ResolutionChecker.checkSysIdResolution;

import javax.xml.catalog.CatalogException;
import javax.xml.catalog.CatalogResolver;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/*
 * @test
 * @bug 8077931
 * @library /javax/xml/jaxp/libs
 * @run testng/othervm catalog.SystemFamilyTest
 * @summary Get matched URIs from system, rewriteSystem, systemSuffix and
 *          delegateSystem entries. It tests the resolution priorities among
 *          the system family entries. The test rule is based on OASIS
 *          Standard V1.1 section 7.1.2. "Resolution of External Identifiers".
 */
public class SystemFamilyTest {

    @Test(dataProvider = "systemId-matchedUri")
    public void testMatch(String systemId, String matchedUri) {
        checkSysIdResolution(createResolver(), systemId, matchedUri);
    }

    @DataProvider(name = "systemId-matchedUri")
    public Object[][] dataOnMatch() {
        return new Object[][] {
                // The matched URI of the specified system id is defined in a
                // system entry.
                { "http://remote/dtd/alice/docAlice.dtd",
                        "http://local/base/dtd/docAliceSys.dtd" },

                // The matched URI of the specified system id is defined in a
                // rewriteSystem entry.
                { "http://remote/dtd/bob/docBob.dtd",
                        "http://local/base/dtd/rs/docBob.dtd" },

                // The matched URI of the specified system id is defined in a
                // systemSuffix entry.
                { "http://remote/dtd/carl/docCarl.dtd",
                         "http://local/base/dtd/docCarlSS.dtd" } };
    }

    /*
     * If no match is found, a CatalogException should be thrown.
     */
    @Test(expectedExceptions = CatalogException.class)
    public void testNoMatch() {
        checkNoMatch(createResolver());
    }

    private CatalogResolver createResolver() {
        return catalogResolver("systemFamily.xml");
    }
}
