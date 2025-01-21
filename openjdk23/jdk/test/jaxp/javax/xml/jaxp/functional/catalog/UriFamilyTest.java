/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package catalog;

import static catalog.CatalogTestUtils.catalogUriResolver;
import static catalog.ResolutionChecker.checkNoUriMatch;
import static catalog.ResolutionChecker.checkUriResolution;

import javax.xml.catalog.CatalogResolver;
import javax.xml.catalog.CatalogException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/*
 * @test
 * @bug 8077931
 * @library /javax/xml/jaxp/libs
 * @run testng/othervm catalog.UriFamilyTest
 * @summary Get matched URIs from uri, rewriteURI, uriSuffix and delegateURI
 *          entries. It tests the resolution priorities among the uri family
 *          entries. The test rule is based on OASIS Standard V1.1 section
 *          7.2.2. "Resolution of External Identifiers".
 */
public class UriFamilyTest {

    @Test(dataProvider = "uri-matchedUri")
    public void testMatch(String systemId, String matchedUri) {
        checkUriResolution(createResolver(), systemId, matchedUri);
    }

    @DataProvider(name = "uri-matchedUri")
    public Object[][] dataOnMatch() {
        return new Object[][] {
                // The matched URI of the specified URI reference is defined in
                // a uri entry.
                { "http://remote/dtd/uri/alice/docAlice.dtd",
                        "http://local/base/dtd/docAliceURI.dtd" },

                // The matched URI of the specified URI reference is defined in
                // a rewriteURI entry.
                { "http://remote/dtd/bob/docBob.dtd",
                        "http://local/base/dtd/ru/docBob.dtd" },

                // The matched URI of the specified URI reference is defined in
                // a uriSuffix entry.
                { "http://remote/dtd/carl/docCarl.dtd",
                        "http://local/base/dtd/docCarlUS.dtd" } };
    }

    /*
     * If no match is found, a CatalogException should be thrown.
     */
    @Test(expectedExceptions = CatalogException.class)
    public void testNoMatch() {
        checkNoUriMatch(createResolver());
    }

    private CatalogResolver createResolver() {
        return catalogUriResolver("uriFamily.xml");
    }
}
