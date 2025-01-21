/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package catalog;

import static catalog.CatalogTestUtils.CATALOG_SYSTEM;
import static catalog.CatalogTestUtils.CATALOG_URI;
import static catalog.CatalogTestUtils.catalogResolver;
import static catalog.CatalogTestUtils.catalogUriResolver;
import static catalog.ResolutionChecker.checkSysIdResolution;
import static catalog.ResolutionChecker.checkUriResolution;

import javax.xml.catalog.CatalogException;

import org.testng.annotations.Test;

/*
 * @test
 * @bug 8077931
 * @library /javax/xml/jaxp/libs
 * @run testng/othervm catalog.ValidateCatalogTest
 * @summary A legal catalog file must be well-formed XML, the root element
 *          must be catalog, and the naming space of the root element must be
 *          urn:oasis:names:tc:entity:xmlns:xml:catalog.
 */
public class ValidateCatalogTest {

    private static final String CATALOG_WRONGROOT = "validateCatalog-wrongRoot.xml";
    private static final String CATALOG_MALFORMED = "validateCatalog-malformed.xml";

    /*
     * EntityResolver tries to load catalog with wrong root,
     * it should throw CatalogException.
     */
    @Test(expectedExceptions = CatalogException.class)
    public void validateWrongRootCatalogOnEntityResolver() {
        catalogResolver(CATALOG_WRONGROOT);
    }

    /*
     * URIResolver tries to load catalog with wrong root,
     * it should throw CatalogException.
     */
    @Test(expectedExceptions = CatalogException.class)
    public void validateWrongRootCatalogOnUriResolver() {
        catalogUriResolver(CATALOG_WRONGROOT);
    }

    /*
     * EntityResolver tries to load malformed catalog,
     * it should throw RuntimeException.
     */
    @Test(expectedExceptions = RuntimeException.class)
    public void validateMalformedCatalogOnEntityResolver() {
        catalogResolver(CATALOG_MALFORMED);
    }

    /*
     * UriResolver tries to load malformed catalog,
     * it should throw RuntimeException.
     */
    @Test(expectedExceptions = RuntimeException.class)
    public void validateMalformedCatalogOnUriResolver() {
        catalogUriResolver(CATALOG_MALFORMED);
    }

    /*
     * Resolver should ignore the catalog which doesn't declare the correct
     * naming space.
     */
    @Test
    public void validateWrongNamingSpaceCatalog() {
        String catalogName = "validateCatalog-noNamingSpace.xml";
        checkSysIdResolution(catalogResolver(catalogName, CATALOG_SYSTEM),
                "http://remote/dtd/alice/docAlice.dtd",
                "http://local/dtd/docAliceSys.dtd");
        checkUriResolution(catalogUriResolver(catalogName, CATALOG_URI),
                "http://remote/dtd/uri/alice/docAlice.dtd",
                "http://local/dtd/docAliceURI.dtd");
    }
}
