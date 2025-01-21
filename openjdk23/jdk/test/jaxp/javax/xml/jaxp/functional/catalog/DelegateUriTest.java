/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package catalog;

import static catalog.CatalogTestUtils.catalogUriResolver;
import static catalog.ResolutionChecker.checkUriResolution;
import static catalog.ResolutionChecker.expectExceptionOnUri;

import javax.xml.catalog.CatalogResolver;
import javax.xml.catalog.CatalogException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/*
 * @test
 * @bug 8077931
 * @library /javax/xml/jaxp/libs
 * @run testng/othervm catalog.DelegateUriTest
 * @summary Get matched URIs from delegateURI entries.
 */
public class DelegateUriTest {

    @Test(dataProvider = "uri-matchedUri")
    public void testMatch(String uri, String matchedUri) {
        checkUriResolution(createResolver(), uri, matchedUri);
    }

    @DataProvider(name = "uri-matchedUri")
    public Object[][] data() {
        return new Object[][] {
                // The matched URI of the specified URI reference is defined in
                // a delegate catalog file of the current catalog file.
                { "http://remote/dtd/alice/docAlice.dtd",
                        "http://local/base/dtd/alice/docAliceDU.dtd" },

                // The current catalog file defines two delegateURI entries
                // with the same uriStartString, and both of them match the
                // specified URI reference. But the matched URI should be in
                // the delegate catalog file, which is defined in the upper
                // delegateURI entry.
                { "http://remote/dtd/bob/docBob.dtd",
                        "http://local/base/dtd/bob/docBobDU.dtd" },

                // The current catalog file defines two delegateURI entries,
                // and both of them match the specified URI reference. But the
                // matched URI should be in the delegate catalog file, which is
                // defined in the longest matched delegateURI entry.
                { "http://remote/dtd/carl/docCarl.dtd",
                        "http://local/base/dtd/carl/docCarlDU.dtd"} };
    }

    @Test(dataProvider = "uri-expectedExceptionClass")
    public void testException(String uri,
            Class<? extends Throwable> expectedExceptionClass) {
        expectExceptionOnUri(createResolver(), uri, expectedExceptionClass);
    }

    @DataProvider(name = "uri-expectedExceptionClass")
    public Object[][] dataOnException() {
        return new Object[][] {
                // The matched delegateURI entry of the specified URI reference
                // defines a non-existing delegate catalog file. That should
                // raise a RuntimeException.
                { "http://remote/dtd/david/docDavidDU.dtd",
                        RuntimeException.class },

                // There's no match of the specified URI reference in the
                // catalog structure. That should raise a CatalogException.
                { "http://ghost/xml/dtd/ghost/docGhostDS.dtd",
                        CatalogException.class } };
    }

    private CatalogResolver createResolver() {
        return catalogUriResolver("delegateUri.xml");
    }
}
