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
 * @run testng/othervm catalog.RewriteUriTest
 * @summary Get matched URIs from rewriteURI entries.
 */
public class RewriteUriTest {

    @Test(dataProvider = "uri-matchedUri")
    public void testMatch(String uri, String matchedUri) {
        checkUriResolution(createResolver(), uri, matchedUri);
    }

    @DataProvider(name = "uri-matchedUri")
    public Object[][] dataOnMatch() {
        return new Object[][] {
                // The matched URI of the specified URI reference is defined in
                // a rewriteURI entry. The match is an absolute path.
                { "http://remote/dtd/alice/docAlice.dtd",
                        "http://local/dtd/alice/ru/docAlice.dtd" },

                // The matched URI of the specified URI reference is defined in
                // a rewriteURI entry. The match isn't an absolute path.
                { "http://remote/dtd/bob/docBob.dtd",
                        "http://local/base/dtd/bob/ru/docBob.dtd" },

                // The matched URI of the specified URI reference is defined in
                // a rewriteURI entry. The match isn't an absolute path, and the
                // rewriteURI entry defines alternative base. So the returned
                // URI should include the alternative base.
                { "http://remote/dtd/carl/docCarl.dtd",
                        "http://local/carlBase/dtd/carl/ru/docCarl.dtd" },

                // The catalog file defines two rewriteURI entries, and both of
                // them match the specified URI reference. But the first matched
                // URI should be returned.
                { "http://remote/dtd/david/docDavid.dtd",
                        "http://local/base/dtd/david1/ru/docDavid.dtd" },

                // The catalog file defines two rewriteURI entries, and both
                // of them match the specified URI reference. But the longest
                // match should be used.
                { "http://remote/dtd/ella/docElla.dtd",
                        "http://local/base/dtd/ella/ru/docElla.dtd" } };
    }

    /*
     * If no match is found, a CatalogException should be thrown.
     */
    @Test(expectedExceptions = CatalogException.class)
    public void testNoMatch() {
        checkNoUriMatch(createResolver());
    }

    private CatalogResolver createResolver() {
        return catalogUriResolver("rewriteUri.xml");
    }
}
