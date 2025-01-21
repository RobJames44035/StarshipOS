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
 * @run testng/othervm catalog.SystemSuffixTest
 * @summary Get matched URIs from systemSuffix entries.
 */
public class SystemSuffixTest {

    @Test(dataProvider = "systemId-matchedUri")
    public void testMatch(String systemId, String matchedUri) {
        checkSysIdResolution(createResolver(), systemId, matchedUri);
    }

    @DataProvider(name = "systemId-matchedUri")
    public Object[][] dataOnMatch() {
        return new Object[][] {
                // The matched URI of the specified system id is defined in a
                // systemIdSuffix entry. The match is an absolute path.
                { "http://remote/dtd/alice/docAlice.dtd",
                        "http://local/dtd/docAliceSS.dtd" },

                // The matched URI of the specified system id is defined in a
                // systemIdSuffix entry. The match isn't an absolute path.
                { "http://remote/dtd/bob/docBob.dtd",
                        "http://local/base/dtd/docBobSS.dtd" },

                // The matched URI of the specified system id is defined in a
                // systemIdSuffix entry. The match isn't an absolute path, and
                // the systemIdSuffix entry defines alternative base. So the
                // returned URI should include the alternative base.
                { "http://remote/dtd/carl/cdocCarl.dtd",
                        "http://local/carlBase/dtd/docCarlSS.dtd" },

                // The catalog file defines two systemIdSuffix entries, and both
                // of them match the specified system id. But the first matched
                // URI should be returned.
                { "http://remote/dtd/david/docDavid.dtd",
                        "http://local/base/dtd/docDavidSS1.dtd" },

                // The catalog file defines two systemIdSuffix entries, and both
                // of them match the specified system id. But the longest match
                // should be used.
                { "http://remote/dtd/ella/docElla.dtd",
                        "http://local/base/dtd/docEllaSS.dtd" } };
    }

    /*
     * If no match is found, a CatalogException should be thrown.
     */
    @Test(expectedExceptions = CatalogException.class)
    public void testNoMatch() {
        checkNoMatch(createResolver());
    }

    private CatalogResolver createResolver() {
        return catalogResolver("systemSuffix.xml");
    }
}
