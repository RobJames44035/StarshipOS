/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package catalog;

import static catalog.CatalogTestUtils.CATALOG_SYSTEM;
import static catalog.CatalogTestUtils.CATALOG_URI;
import static catalog.CatalogTestUtils.RESOLVE_CONTINUE;
import static catalog.CatalogTestUtils.RESOLVE_IGNORE;
import static catalog.CatalogTestUtils.RESOLVE_STRICT;
import static catalog.CatalogTestUtils.catalogResolver;
import static catalog.CatalogTestUtils.catalogUriResolver;
import static catalog.ResolutionChecker.checkSysIdResolution;
import static catalog.ResolutionChecker.checkUriResolution;
import static javax.xml.catalog.CatalogFeatures.builder;

import javax.xml.catalog.CatalogException;
import javax.xml.catalog.CatalogFeatures;
import javax.xml.catalog.CatalogFeatures.Feature;
import javax.xml.catalog.CatalogResolver;

import org.testng.annotations.Test;

/*
 * @test
 * @bug 8077931
 * @library /javax/xml/jaxp/libs
 * @run testng/othervm catalog.ResolveFeatureTest
 * @summary This case tests how does resolve feature affect the catalog
 *          resolution.
 */
public class ResolveFeatureTest {

    /*
     * For strict external identifier resolution, if no match is found,
     * it should throw CatalogException.
     */
    @Test(expectedExceptions = CatalogException.class)
    public void testStrictResolutionOnEntityResolver() {
        createEntityResolver(RESOLVE_STRICT).resolveEntity(null,
                "http://remote/dtd/alice/docAliceDummy.dtd");
    }

    /*
     * For strict URI reference resolution, if no match is found,
     * it should throw CatalogException.
     */
    @Test(expectedExceptions = CatalogException.class)
    public void testStrictResolutionOnUriResolver() {
        createUriResolver(RESOLVE_STRICT).resolve(
                "http://remote/dtd/alice/docAliceDummy.dtd", null);
    }

    /*
     * For continue external identifier resolution, if no match is found,
     * it should continue the process.
     */
    @Test
    public void testContinueResolutionOnEntityResolver() {
        CatalogResolver resolver = createEntityResolver(RESOLVE_CONTINUE);
        resolver.resolveEntity(null, "http://remote/dtd/bob/docBobDummy.dtd");
        checkSysIdResolution(resolver, "http://remote/dtd/bob/docBob.dtd",
                "http://local/base/dtd/docBobSys.dtd");
    }

    /*
     * For continue URI reference resolution, if no match is found,
     * it should continue the process.
     */
    @Test
    public void testContinueResolutionOnUriResolver() {
        CatalogResolver resolver = createUriResolver(RESOLVE_CONTINUE);
        resolver.resolve("http://remote/dtd/bob/docBobDummy.dtd", null);
        checkUriResolution(resolver, "http://remote/dtd/bob/docBob.dtd",
                "http://local/base/dtd/docBobURI.dtd");
    }

    /*
     * For ignore external identifier resolution, if no match is found,
     * it should break the process and return null.
     */
    @Test
    public void testIgnoreResolutionOnEntityResolver() {
        checkSysIdResolution(createEntityResolver(RESOLVE_IGNORE),
                "http://remote/dtd/carl/docCarlDummy.dtd", null);
    }

    /*
     * For ignore URI reference resolution, if no match is found,
     * it should break the process and return null.
     */
    @Test
    public void testIgnoreResolutionOnUriResolver() {
        checkUriResolution(createUriResolver(RESOLVE_IGNORE),
                "http://remote/dtd/carl/docCarlDummy.dtd", null);
    }

    private CatalogResolver createEntityResolver(String resolve) {
        return catalogResolver(createFeature(resolve), CATALOG_SYSTEM);
    }

    private CatalogResolver createUriResolver(String resolve) {
        return catalogUriResolver(createFeature(resolve), CATALOG_URI);
    }

    private CatalogFeatures createFeature(String resolve) {
        return builder().with(Feature.RESOLVE, resolve).build();
    }
}
