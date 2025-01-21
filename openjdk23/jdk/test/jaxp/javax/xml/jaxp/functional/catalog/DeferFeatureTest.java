/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package catalog;

import static catalog.CatalogTestUtils.DEFER_FALSE;
import static catalog.CatalogTestUtils.DEFER_TRUE;
import static catalog.CatalogTestUtils.getCatalogPath;
import static javax.xml.catalog.CatalogFeatures.Feature.DEFER;

import java.lang.reflect.Method;

import javax.xml.catalog.Catalog;
import javax.xml.catalog.CatalogException;
import javax.xml.catalog.CatalogFeatures;
import javax.xml.catalog.CatalogManager;
import javax.xml.catalog.CatalogResolver;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/*
 * @test
 * @bug 8077931 8176405
 * @library /javax/xml/jaxp/libs
 * @modules java.xml/javax.xml.catalog:open
 * @run testng/othervm catalog.DeferFeatureTest
 * @summary This case tests whether the catalogs specified in delegateSystem,
 *          delegatePublic, delegateURI and nextCatalog entries are used lazily
 *          in resolution via defer feature.
 */
public class DeferFeatureTest {

    @Test(dataProvider = "catalog-countOfLoadedCatalogFile")
    public void testDeferFeature(Catalog catalog, int catalogCount)
            throws Exception {
        Assert.assertEquals(loadedCatalogCount(catalog), catalogCount);
    }

    @Test(dataProvider = "testDeferFeatureByResolve")
    public void testDeferFeatureByResolve(Catalog catalog, int catalogCount)
            throws Exception {
        CatalogResolver cr = createResolver(catalog);
        // trigger loading alternative catalogs
        try {
            cr.resolveEntity("-//REMOTE//DTD ALICE DOCALICE", "http://remote/dtd/alice/");
        } catch (CatalogException ce) {}

        Assert.assertEquals(loadedCatalogCount(catalog), catalogCount);
    }

    @DataProvider(name = "catalog-countOfLoadedCatalogFile")
    public Object[][] data() {
        return new Object[][]{
            // By default, alternative catalogs are not loaded.
            {createCatalog(CatalogFeatures.defaults()), 1},
            // Alternative catalogs are not loaded when DEFER is set to true.
            {createCatalog(createDeferFeature(DEFER_TRUE)), 1},
            // The 3 alternative catalogs are pre-loaded along with the parent
            //when DEFER is set to false.
            {createCatalog(createDeferFeature(DEFER_FALSE)), 4}};
    }

    @DataProvider(name = "testDeferFeatureByResolve")
    public Object[][] getData() {
        return new Object[][]{
            {createCatalog(createDeferFeature(DEFER_TRUE)), 4}
        };
    }

    private CatalogFeatures createDeferFeature(String defer) {
        return CatalogFeatures.builder().with(DEFER, defer).build();
    }

    private Catalog createCatalog(CatalogFeatures feature) {
        return CatalogManager.catalog(feature, getCatalogPath("deferFeature.xml"));
    }

    private CatalogResolver createResolver(Catalog catalog) {
        return CatalogManager.catalogResolver(catalog);
    }

    private int loadedCatalogCount(Catalog catalog) throws Exception {
        Method method = catalog.getClass().getDeclaredMethod("loadedCatalogCount");
        method.setAccessible(true);
        return (int) method.invoke(catalog);
    }
}
