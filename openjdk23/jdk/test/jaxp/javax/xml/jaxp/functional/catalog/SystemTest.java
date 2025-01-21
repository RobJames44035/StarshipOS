/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package catalog;

import static catalog.CatalogTestUtils.CATALOG_SYSTEM;
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
 * @run testng/othervm catalog.SystemTest
 * @summary Get matched URIs from system entries.
 */
public class SystemTest {

    @Test(dataProvider = "systemId-matchedUri")
    public void testMatch(String systemId, String matchedUri) {
        checkSysIdResolution(createResolver(), systemId, matchedUri);
    }

    @DataProvider(name = "systemId-matchedUri")
    public Object[][] dataOnMatch() {
        return new Object[][] {
                // The matched URI of the specified system id is defined in a
                // system entry. The match is an absolute path.
                { "http://remote/dtd/alice/docAlice.dtd",
                        "http://local/dtd/docAliceSys.dtd" },

                // The matched URI of the specified system id is defined in a
                // public entry. But the match isn't an absolute path, so the
                // returned URI should include the base, which is defined by the
                // catalog file, as the prefix.
                { "http://remote/dtd/bob/docBob.dtd",
                        "http://local/base/dtd/docBobSys.dtd" },

                // The matched URI of the specified system id is defined in a
                // system entry. The match isn't an absolute path, and the
                // system entry defines alternative base. So the returned URI
                // should include the alternative base.
                { "http://remote/dtd/carl/docCarl.dtd",
                        "http://local/carlBase/dtd/docCarlSys.dtd" },

                // The catalog file defines two system entries, and both of them
                // match the specified system id. But the first matched URI
                // should be returned.
                { "http://remote/dtd/david/docDavid.dtd",
                        "http://local/base/dtd/docDavidSys1.dtd" } };
    }

    /*
     * If no match is found, a CatalogException should be thrown.
     */
    @Test(expectedExceptions = CatalogException.class)
    public void testNoMatch() {
        checkNoMatch(createResolver());
    }

    private CatalogResolver createResolver() {
        return catalogResolver(CATALOG_SYSTEM);
    }
}
